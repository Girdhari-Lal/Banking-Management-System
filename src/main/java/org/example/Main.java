package org.example;

import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.service.AccountService;
import org.example.service.CustomerService;
import org.hibernate.Session;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        CustomerService customerService = new CustomerService();
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("""
                Welcome to the Banking Management System
                1. Open Account
                2. Close Account
                3. Deposit Money
                4. Withdraw Money
                5. Update Information
                6. Generate Report
                7. Exit""");
            System.out.print("Enter number: ");
            int num = sc.nextInt();
            Session session = FactoryProvider.getSession();
            if (num == 1) {
                Customer customer = customerService.findCustomerByCnic(session);
                customerService.setCustomerInfo(session, customer);
                System.out.print("Do you want open account (y/n): ");
                char letter = sc.next().charAt(0);
                if(letter=='y'){
                    Account account = accountService.addAccount();
                    accountService.setAccountInfo(session, customer, account);
                 }
            } else if (num == 2) {
                accountService.closeAccount(session);
            } else if (num == 3) {
                accountService.depositAmount(sc,session);
            } else if (num == 4) {
                accountService.withdrawAmount(session);
            } else if (num == 5) {
                customerService.updateInfo(sc,session);
            } else if (num == 6) {
                Report report = new Report();
                report.criteria(sc,session);
            } else{
                FactoryProvider.closeSession();
                return;
            }
        }
    }
}