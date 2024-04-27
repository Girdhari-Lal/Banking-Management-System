package org.example;

import org.example.constant.AccountType;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Report {
    public void criteria(Scanner sc,Session session){
        System.out.println("""
                Select Search Criteria
                1. New accounts open today
                2. Close balance today
                3. Withdrawals account today
                4. Summaries of account type
                5. Show all the customer
                7. Exit""");
        System.out.print("Enter number: ");
        int num = sc.nextInt();
        if(num == 1){
            openAccountToday(session);
        } else if (num==2) {
            printClosingBalance(session);
        } else if (num==3) {
            withdrawToday(session);
        } else if (num==4) {
            accountTypeCount(session);
        } else if (num==5) {
            detailAllCustomer(session);
        }
    }
    public void openAccountToday(Session session){
        Transaction tx = session.beginTransaction();
        String query = "From Customer where date=:date";
        Query q = session.createQuery(query);
        q.setParameter("date", LocalDate.now());
        List<Customer> list=q.list();
        if(!list.isEmpty()) {
            for (Customer detail : list) {
                System.out.println("Name: " + detail.getFirstName() +" "+ detail.getLastName() + ", Occupation: " + detail.getOccupation() + ", CNIC: " + detail.getCnic());
            }
        }else {
            System.out.println("No new account open today");
        }
        tx.commit();
    }
    public void detailAllCustomer(Session session){
        Transaction tx = session.beginTransaction();
        String query = "From Customer";
        Query q = session.createQuery(query);
        List<Customer> list = q.list();
        for(Customer customer:list){
            System.out.println("Name: "+customer.getFirstName()+" "+customer.getLastName()+", CNIC: "+customer.getCnic()+", Occupation: "+customer.getOccupation());
        }
        tx.commit();
    }
    public void accountTypeCount(Session session){
        int basic = 0, saving = 0, current = 0;
        Transaction tx = session.beginTransaction();
        String query = "From Account where isOpen=true";
        Query q = session.createQuery(query);
        List<Account> list=q.list();
        for(Account account:list){
            if(account.getType()== AccountType.BASIC){
                basic++;
            } else if (account.getType()==AccountType.SAVING) {
                saving++;
            } else if (account.getType()==AccountType.CURRENT) {
                current++;
            }
        }
        System.out.println("Basic Banking Account: "+basic);
        System.out.println("Saving Account: "+saving);
        System.out.println("Current Account: "+current);
        tx.commit();
    }
    public void withdrawToday(Session session) {
        Transaction tx = session.beginTransaction();
        String query = "SELECT t.amount, a.currency, a.type, c.first_name, c.last_name \n" +
                "FROM account_transaction t\n" +
                "JOIN Account a ON t.account_id = a.id\n" +
                "JOIN Customer c ON a.customer_id = c.id\n" +
                "WHERE t.date = :date  and a.is_open=1\n";
        Query q = session.createSQLQuery(query);
        q.setParameter("date", LocalDate.now());
        List<Object[]> resultList = q.list();
        if(resultList.isEmpty()) {
            System.out.println("No withdraw amount today");
        }
        for (Object[] row : resultList) {
            Float amount = (Float) row[0];
            String currency = (String) row[1];
            String accountType = (String) row[2];
            String firstName = (String) row[3];
            String lastName = (String) row[4];
            System.out.println("Withdraw: " + amount + ", Currency: " + currency + ", Account type: " + accountType + ", account Name: " + firstName + " " + lastName);
        }
        tx.commit();
    }
    public void printClosingBalance(Session session){
        Transaction tx = session.beginTransaction();
        String query = "From Account where isOpen=true";
        Query q = session.createQuery(query);
        List<Account> list = q.list();
        float balance = 0;
        for(Account account:list){
            balance = balance+account.getBalance();
        }
        System.out.println("Total Closing Balance Today: Rs. "+ balance);
        tx.commit();
    }
}
