package org.example.service;

import org.example.entity.Account;
import org.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Scanner;

public class CustomerService {
    public Customer findOrAddCustomer(Session session) {
        CustomerInputService customerInputService = new CustomerInputService();
        String cnic = customerInputService.getCnic();
        Customer customer = findCustomerByCnic(session,cnic);
        if (customer == null) {
            customer = customerInputService.inputCustomerDetail(cnic);
        }
        return customer;
    }
    public Customer findCustomerByCnic(Session session, String cnic){
        String query = "FROM Customer WHERE cnic =: cnic";
        Query q = session.createQuery(query);
        q.setParameter("cnic", cnic);
        Object result = q.uniqueResult();
        return (Customer) result;
    }
    public void setCustomerInfo(Session session, Customer customer){
        Transaction tx = session.beginTransaction();
        session.save(customer);
        System.out.println("Detail add Successfully!");
        tx.commit();
    }
    public void updateInfo(Scanner sc,Session session) {
        Transaction tx = session.beginTransaction();
        AccountService accountService = new AccountService();
        Account account = accountService.loginAccount(session);
        int accountId = account.getId();
        String accountPassword = account.getPassword();
                System.out.print("""
                Select Information to Update:
                1. First name
                2. Last name
                3. Address
                4. Occupation
                Enter Selection:\s""");
        int num = sc.nextInt();
        Query q = null;
        String query = null;
        if (num==1){
            System.out.print("Enter first name: ");
            String firstName = sc.next();
            query = "update customer c INNER JOIN account a ON c.id = a.customer_id set c.first_name=:name where a.id="+accountId+" and a.password="+accountPassword+"  and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("name",firstName);
        } else if(num==2){
            System.out.print("Enter last name: ");
            String lastName = sc.next();
            query = "update customer c INNER JOIN account a ON c.id = a.customer_id set c.last_name=:name where a.id="+accountId+" and a.password="+accountPassword+" and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("name",lastName);
        } else if(num==3){
            System.out.print("Enter Address: ");
            sc.nextLine();
            String address = sc.nextLine();
            query = "update customer c INNER JOIN account a ON c.id = a.customer_id set c.address=:address where a.id="+accountId+" and a.password="+accountPassword+" and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("address",address);
        } else if (num==4) {
            System.out.print("Enter Occupation: ");
            String occupation = sc.next();
            query = "update customer c INNER JOIN account a ON c.id = a.customer_id set c.occupation=:occupation where a.id="+accountId+" and a.password="+accountPassword+" and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("occupation",occupation);
        }else {
            System.out.println("invalid input");
            System.exit(0);
        }
        q.executeUpdate();
        System.out.println("Updated Successfully!");
        tx.commit();
    }
}
