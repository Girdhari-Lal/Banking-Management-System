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
                6. Exit""");
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
        String query = "Select c.first_name, c.last_name, c.sex, c.cnic, a.type, a.balance from account a inner join customer c on c.id = a.customer_id and a.is_open=1 and date_created=:date_created";
        Query q = session.createSQLQuery(query);
        q.setParameter("date_created", LocalDate.now());
        List<Object[]> list = q.list();
        if(!list.isEmpty()) {
            for (Object[] row : list) {
                System.out.println("Name: "+row[0]+" "+row[1]+", Gender: " +row[2]+ ", CNIC: "+row[3]+ ", Account Type: " +row[4]+ ", Balance: " +row[5]);
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
        Transaction tx = session.beginTransaction();
        String query = "SELECT type, COUNT(type) FROM account where is_open = true group by type";
        Query q = session.createSQLQuery(query);
        List<Object[]> list = q.list();
        for(Object[] row : list){
            System.out.println(row[0]+" : "+row[1]);
        }
        tx.commit();
    }
    public void withdrawToday(Session session) {
        Transaction tx = session.beginTransaction();
        String query = "SELECT t.amount, a.currency, a.type, c.first_name, c.last_name \n" +
                "FROM account_transaction t\n" +
                "JOIN Account a ON t.account_id = a.id\n" +
                "JOIN Customer c ON a.customer_id = c.id\n" +
                "WHERE t.date = :date and a.is_open=1 and t.amount like '-%'\n";
        Query q = session.createSQLQuery(query);
        q.setParameter("date", LocalDate.now());
        List<Object[]> resultList = q.list();
        if(resultList.isEmpty()) {
            System.out.println("No withdraw amount today");
        }
        for (Object[] row : resultList) {
            System.out.println("Withdraw: " + row[0] + ", Currency: " + row[1] + ", Account type: " + row[2] + ", Name: " + row[3] + " " + row[4]);
        }
        tx.commit();
    }
    public void printClosingBalance(Session session){
        Transaction tx = session.beginTransaction();
        String query = "SELECT sum(balance) FROM account WHERE is_open=1;";
        Query q = session.createSQLQuery(query);
        Double totalAmount =(Double) q.uniqueResult();
        System.out.println("Total Closing Balance Today: Rs. "+ totalAmount);
        tx.commit();
    }
}
