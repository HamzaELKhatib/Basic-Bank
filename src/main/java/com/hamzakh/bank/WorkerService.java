package com.hamzakh.bank;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void registerWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public void deregisterWorker(Long id) {
        workerRepository.deleteById(id);
    }

    public void increaseSalary(Long id, double amount) {
        Worker worker = workerRepository.findById(id).orElse(null);
        if (worker != null) {
            worker.setGrossSalary(worker.getGrossSalary() + amount);
            workerRepository.save(worker);
        }
    }

    public void paySalaries() {
        List<Worker> workers = workerRepository.findAll();
        for (Worker worker : workers) {
            double netAmount = worker.getGrossSalary() * 0.9475; // 5.25% income tax
            worker.setBalance(worker.getBalance() + netAmount);
            workerRepository.save(worker);
        }
    }

    public void makeTransfer(Long senderId, Long recipientId, double amount) {

        // Check if sender and recipient IDs exist
        if (!workerRepository.existsById(senderId)) {
            throw new FailedTransactionException("sender ID does not exist");
        }
        if (!workerRepository.existsById(recipientId)) {
            throw new FailedTransactionException("recipient ID does not exist");
        }

        // Easter Egg 1: transactions to or from an "Antonio" must fail
        Worker sender = workerRepository.findById(senderId).orElse(null);
        if (sender != null && "Antonio".equals(sender.getName())) {
            throw new FailedTransactionException("transactions to or from an 'Antonio' are not allowed");
        }
        Worker recipient = workerRepository.findById(recipientId).orElse(null);
        if (recipient != null && "Antonio".equals(recipient.getName())) {
            throw new FailedTransactionException("transactions to or from an 'Antonio' are not allowed");
        }

        // Easter Egg 2: transactions with an amount that is not a multiple of €10 must fail
        if (amount % 10 != 0) {
            throw new FailedTransactionException("transactions with an amount that is not a multiple of €10 are not allowed");
        }

        if (sender != null && recipient != null) {
            if (sender.getBalance() >= amount) {
                sender.setBalance(sender.getBalance() - amount);
                recipient.setBalance(recipient.getBalance() + amount);
                workerRepository.save(sender);
                workerRepository.save(recipient);
            } else {
                throw new FailedTransactionException("insufficient balance");
            }
        }
    }
}
