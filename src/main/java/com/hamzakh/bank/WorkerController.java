package com.hamzakh.bank;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping
    public void registerWorker(@RequestBody Worker worker) {
        workerService.registerWorker(worker);
    }

    @DeleteMapping("/{id}")
    public void deregisterWorker(@PathVariable Long id) {
        workerService.deregisterWorker(id);
    }

    @PatchMapping("/{id}/salary")
    public void increaseSalary(@PathVariable Long id, @RequestParam double amount) {
        workerService.increaseSalary(id, amount);
    }

    @PostMapping("/pay-salaries")
    public void paySalaries(@RequestHeader(value="Authorization", required=false) String authHeader) {
        if (!"Gandalf".equals(authHeader)) { // partially secured endpoint
            throw new RuntimeException("Unauthorized");
        }
        workerService.paySalaries();
    }

    @PostMapping("/transfers")
    public void makeTransfer(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestParam double amount) {
        workerService.makeTransfer(senderId, recipientId, amount);
    }
}