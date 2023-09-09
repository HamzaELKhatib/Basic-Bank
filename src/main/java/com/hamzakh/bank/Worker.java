package com.hamzakh.bank;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Worker {
    @Id
    @SequenceGenerator(name = "worker_id_sequence", sequenceName = "worker_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worker_id_sequence")
    private Long id;
    private String name;
    private String lastName;
    private double grossSalary;
    private double balance;

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Worker worker = (Worker) o;
        return getId() != null && Objects.equals(getId(), worker.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}