package org.example.service;

import org.example.entity.Account;
import org.example.AccountTransaction;
import org.example.entity.Customer;
import org.example.constant.AccountType;
import org.example.constant.CurrencyType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Scanner;

public class AccountService {
    Scanner sc = new Scanner(System.in);
    public void setAccountInfo(Session session, Customer customer, Account account){
        Transaction tx = session.beginTransaction();
        account.setCustomer(customer);
        session.save(account);
        System.out.println("Account Opened Successfully!");
        tx.commit();
    }
    public Account loginAccount(Session session){
        AccountInputService accountInputService = new AccountInputService();
        System.out.print("Enter account ID: ");
        int accountId = sc.nextInt();
        String accountPassword = accountInputService.getAccountPassword();
        String query = "From Account where id=:id and password=:password and isOpen=true";
        Query q = session.createQuery(query);
        q.setParameter("id",accountId);
        q.setParameter("password",accountPassword);
        Object accountResult = q.uniqueResult();
        if(accountResult==null){
            System.out.println("invalid input");
            System.out.print("Do you want again input password(y/n): ");
            char latter  = sc.next().charAt(0);
            if(latter=='y') {
                 return loginAccount(session);
            }else {
                System.exit(0);
            }
        }
        return (Account) accountResult;
    }
    public void depositAmount(Scanner sc,Session session){
        Transaction tx = session.beginTransaction();
        Account account = loginAccount(session);
        System.out.print("Enter Amount to Deposit: ");
        float deposit = sc.nextFloat();
        if(deposit>0) {
            AccountTransaction transaction = new AccountTransaction();
            transaction.setAccount(account);
            float currentBalance = account.getBalance();
            float updatedBalance = currentBalance + deposit;
            setAccountBalanceDetails(updatedBalance, deposit, transaction, account, session);
            System.out.println("Deposit Successfully");
        }else {
            System.out.println(deposit + " is not valid amount");
        }
        tx.commit();
    }
    public void withdrawAmount(Session session){
        Transaction tx = session.beginTransaction();
        Account account = loginAccount(session);
        AccountType type = account.getType();
        float amount = account.getBalance();
        System.out.print("Enter Amount to Withdraw: ");
        float withdrawAmount = sc.nextFloat();
        float money = amount-withdrawAmount;
        float withdrawSignAmount = withdrawAmount * -1;
        if(withdrawAmount<=0){
            System.out.println(withdrawAmount + " is not valid amount");
        } else if((type == AccountType.SAVING && money >= 5000) || (type == AccountType.BASIC && money >= 1000) || (type == AccountType.CURRENT && amount >= withdrawAmount)) {
            AccountTransaction transaction = new AccountTransaction();
            transaction.setAccount(account);
            setAccountBalanceDetails(money, withdrawSignAmount, transaction, account, session);
            System.out.println("Withdraw Successful!");
        }else {
            System.out.println("Insufficient balance");
        }
        tx.commit();
    }
    public void setAccountBalanceDetails(float money, float withdrawAmount, AccountTransaction transcation, Account account, Session session){
        account.setBalance(money);
        transcation.setAmount(withdrawAmount);
        session.save(transcation);
    }
    public void closeAccount(Session session){
        Account account = loginAccount(session);
        Transaction tx = session.beginTransaction();
        account.setOpen(false);
        session.save(account);
        System.out.println("Account Closed Successfully!");
        tx.commit();
    }
}
