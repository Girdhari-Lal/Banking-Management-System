package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Scanner;

public class AccountService {
    Scanner sc = new Scanner(System.in);
    public Account addAccount(){
        Account account = new Account();
        int type = getAccountType(account);
        setCurrency(account);
        setBalance(account,type);
        System.out.print("Password: ");
        String password = sc.next();
        account.setOpen(true);
        account.setPassword(password);
        return account;
    }
    public Object loginAccount(Session session,int accountId, String accountPassword){
        String query = "From Account where id=:id and password=:password and isOpen=true";
        Query q = session.createQuery(query);
        q.setParameter("id",accountId);
        q.setParameter("password",accountPassword);
        Object result = q.uniqueResult();
        if(result==null){
            System.out.println("invalid input");
            System.out.println("Do you want again input password: y/n ");
            char latter  = sc.next().charAt(0);
            if(latter=='y') {
                return loginAccount(session, accountId, accountPassword);
            }else {
                System.exit(0);
            }
        }
        return result;
    }
    public void depositAmount(Scanner sc,Session session){
        Transaction tx = session.beginTransaction();
        AccountTransaction transaction = new AccountTransaction();
        int accountId = getAccountId();
        String accountPassword = getAccountPassword();
        Object result = loginAccount(session, accountId, accountPassword);
        Account account = (Account) result;
        System.out.print("Enter Amount to Deposit: ");
        float deposit = sc.nextFloat();
        //int accountId = account.getId();
        //account = session.get(Account.class,accountId);
        transaction.setAccount(account);
        float balance = account.getBalance();
        balance += deposit;
        String val = "+"+String.valueOf(deposit);
        transaction.setAmount(val);
        account.setBalance(balance);
        session.save(transaction);
        System.out.println("Deposit Successfully");
        tx.commit();
    }
    public void withdrawAmount(Session session){
        Transaction tx = session.beginTransaction();
        AccountTransaction transcation = new AccountTransaction();
        int accountId = getAccountId();
        String accountPassword = getAccountPassword();
        Object result = loginAccount(session, accountId, accountPassword);
        Account account = (Account) result;
        //int accountId = account.getId();
        //account = session.get(Account.class,accountId);
        float amount = account.getBalance();
        transcation.setAccount(account);
        AccountType type = account.getType();
        System.out.print("Enter Amount to Withdraw: ");
        float withdrawAmount = sc.nextFloat();
        float money = amount-withdrawAmount;
        String val = "-"+String.valueOf(withdrawAmount);
        if(type==AccountType.SAVING && money>=5000){
            account.setBalance(money);
            transcation.setAmount(val);
            session.save(transcation);
        } else if ((type==AccountType.BASIC || type==AccountType.CURRENT) && amount>=withdrawAmount) {
            account.setBalance(money);
            transcation.setAmount(val);
            session.save(transcation);
        }else {
            System.out.println("Insufficient balance");
            tx.commit();
            return;
        }
        System.out.println("Withdraw Successful!");
        tx.commit();
    }
    public void setAccountType(Account account,int accountType){
        if(accountType==1){
            account.setType(AccountType.BASIC);
        } else if (accountType==2) {
            account.setType(AccountType.SAVING);
        } else if(accountType==3){
            account.setType(AccountType.CURRENT);
        }else {
            System.out.println("Invalid account type");
            setAccountType(account,accountType);
        }
    }
    public int getAccountType(Account account){
        System.out.print("""
                1. Basic Banking Account
                 Account can be opened with an initial/minimum deposit of Rs.1, 000.
                2. Saving Account
                 Account can be opened with an initial/minimum deposit of Rs.5,000
                3. Current Account
                 No Minimum Balance
                Select Account Type:\s""");
        int accountType = sc.nextInt();
        setAccountType(account,accountType);
        return accountType;
    }
    public void setCurrency(Account account){
        System.out.print("""
                1. USD
                2. EUR
                3. PKR
                4. YEN
                """);
        System.out.print("Enter currency name: ");
        String currency = sc.next().toUpperCase();
        if(currency.equals("USD") || currency.equals("EUR") || currency.equals("PKR" ) || currency.equals("YEN")){
            account.setCurrency(CurrencyType.valueOf(currency));
        } else {
            System.out.println("Invalid currency");
            setCurrency(account);
        }
    }
    public void setBalance(Account account,int type){
        System.out.print("Enter amount to Deposit: ");
        float balance = sc.nextFloat();
        if ((type == 1 && balance >= 1000) || (type == 2 && balance>= 5000) || (type == 3 && balance>=0)) {
            account.setBalance(balance);
            return;
        }
        System.out.println("Invalid amount of balance");
        setBalance(account,type);
    }
    public int getAccountId(){
        System.out.print("Enter account ID: ");
        int accountId = sc.nextInt();
        return accountId;
    }
    public String getAccountPassword(){
        System.out.print("Enter Password: ");
        String accountPassword = sc.next();
        return accountPassword;
    }
    public void closeAccount(Session session){
        int accountId = getAccountId();
        String accountPassword = getAccountPassword();
        Object result = loginAccount(session, accountId, accountPassword);
        Transaction tx = session.beginTransaction();
        Account account = (Account) result;
        account.setOpen(false);
        session.save(account);
        System.out.println("Account Closed Successfully!");
        tx.commit();
    }
}
