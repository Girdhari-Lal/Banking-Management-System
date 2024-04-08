package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "balance", nullable = false)
    private float balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;
    @Enumerated(EnumType.STRING)
    @Column(name = "type" , nullable = false)
    private AccountType accountType;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_open", nullable = false)
    private boolean isOpen;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    public int getId() {
        return id;
    }
    public float getBalance() {
        return balance;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }
    public CurrencyType getCurrency() {
        return currency;
    }
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public AccountType getType() {
        return accountType;
    }
    public void setType(AccountType type) {
        this.accountType = type;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }
}