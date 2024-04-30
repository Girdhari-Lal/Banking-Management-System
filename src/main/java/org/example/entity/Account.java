package org.example.entity;

import org.example.constant.AccountType;
import org.example.constant.CurrencyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "balance", nullable = false)
    private float balance;
    @Column(name = "is_open", nullable = false)
    private boolean isOpen;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "date_created", nullable = false)
    private LocalDate date_created = LocalDate.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;
    @Enumerated(EnumType.STRING)
    @Column(name = "type" , nullable = false)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    public int getId() {
        return id;
    }
    public float getBalance() {
        return balance;
    }
    public AccountType getType() {
        return accountType;
    }
    public String getPassword() {
        return password;
    }
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setType(AccountType type) {
        this.accountType = type;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }
}