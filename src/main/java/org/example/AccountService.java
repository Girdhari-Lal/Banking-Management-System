package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Scanner;

public class AccountService {
    Scanner sc = new Scanner(System.in);
    public Account addAccount(){
        Account account = new Account();
        AccountInputService accountInputService = new AccountInputService();
        AccountType accountType = accountInputService.getAccountType();
        CurrencyType currencyType = accountInputService.getCurrency();
        float balance = accountInputService.getBalance(accountType);
        String password = accountInputService.getAccountPassword();
        account.setBalance(balance);
        account.setType(accountType);
        account.setCurrency(currencyType);
        account.setOpen(true);
        account.setPassword(password);
        return account;
    }
    public Account loginAccount(Session session){
        AccountInputService accountInputService = new AccountInputService();
        int accountId = accountInputService.getAccountId();
        String accountPassword = accountInputService.getAccountPassword();
        String query = "From Account where id=:id and password=:password and isOpen=true";
        Query q = session.createQuery(query);
        q.setParameter("id",accountId);
        q.setParameter("password",accountPassword);
        Object accountResult = q.uniqueResult();
        Account account = (Account) accountResult;
        if(accountResult==null){
            System.out.println("invalid input");
            System.out.println("Do you want again input password: y/n ");
            char latter  = sc.next().charAt(0);
            if(latter=='y') {
                 return loginAccount(session);
            }else {
                System.exit(0);
            }
        }
        return account;
    }
    public void depositAmount(Scanner sc,Session session){
        Transaction tx = session.beginTransaction();
        AccountTransaction transaction = new AccountTransaction();
        Account account = loginAccount(session);
        System.out.print("Enter Amount to Deposit: ");
        float deposit = sc.nextFloat();
        //int accountId = account.getId();
        //account = session.get(Account.class,accountId);
        transaction.setAccount(account);
        float balance = account.getBalance();
        balance += deposit;
        setAccountService(balance, deposit, transaction, account, session);
        System.out.println("Deposit Successfully");
        tx.commit();
    }
    public void withdrawAmount(Session session){
        Transaction tx = session.beginTransaction();
        AccountTransaction transcation = new AccountTransaction();
        Account account = loginAccount(session);
        //int accountId = account.getId();
        //account = session.get(Account.class,accountId);
        float amount = account.getBalance();
        transcation.setAccount(account);
        AccountType type = account.getType();
        System.out.print("Enter Amount to Withdraw: ");
        float withdrawAmount = sc.nextFloat();
        float money = amount-withdrawAmount;
        if(type==AccountType.SAVING && money>=5000){
            setAccountService(money, -withdrawAmount, transcation, account, session);
        } else if ((type==AccountType.BASIC || type==AccountType.CURRENT) && amount>=withdrawAmount) {
            setAccountService(money, -withdrawAmount, transcation, account, session);
        }else {
            System.out.println("Insufficient balance");
            tx.commit();
            return;
        }
        System.out.println("Withdraw Successful!");
        tx.commit();
    }
    public void setAccountService(float money, float withdrawAmount, AccountTransaction transcation, Account account, Session session){
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
