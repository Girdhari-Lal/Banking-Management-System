package org.example;

import org.example.entity.Account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "account_transaction")
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "amount", nullable = false)
    private float amount;
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    public void setAccount(Account account) {
        this.account = account;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
}
