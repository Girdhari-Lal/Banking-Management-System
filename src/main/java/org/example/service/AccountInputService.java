package org.example.service;

import org.example.constant.AccountType;
import org.example.constant.CurrencyType;
import org.example.entity.Account;

import java.util.Scanner;

public class AccountInputService {
    Scanner sc = new Scanner(System.in);
    public AccountType getAccountType(){
        System.out.print("""
                1. Basic Banking Account
                 Account can be opened with an initial/minimum deposit of Rs.1, 000.
                2. Saving Account
                 Account can be opened with an initial/minimum deposit of Rs.5,000
                3. Current Account
                 No Minimum Balance
                Select Account Type:\s""");
        int type = sc.nextInt();
        for (AccountType accountType : AccountType.values()) {
            if (accountType.getValue() == type) {
                return accountType;
            }
        }
        System.out.println("Invalid input! Enter again");
        return getAccountType();
    }
    public CurrencyType getCurrency(){
        System.out.print("""
                1. USD
                2. EUR
                3. PKR
                4. YEN
                """);
        System.out.print("Enter currency name: ");
        String currency = sc.next().toUpperCase();
        try{
            return CurrencyType.valueOf(currency);
        }catch (IllegalArgumentException e){
            System.out.println("Invalid currency! Enter again your currency: ");
        }
        return getCurrency();
    }
    public float getBalance(AccountType type){
        System.out.print("Enter amount to Deposit: ");
        float balance = sc.nextFloat();
        if ((type == AccountType.BASIC && balance >= 1000) || (type == AccountType.SAVING && balance>= 5000) || (type == AccountType.CURRENT && balance>=0)) {
            return balance;
        }else if(type == AccountType.BASIC && balance < 1000) {
            System.out.println("Deposit amount is not greater than 1000");
        }else if(type == AccountType.SAVING && balance < 5000){
            System.out.println("Deposit amount is not greater than 1000");
        } else{
            System.out.println("Error! Enter Again");
        }
        return getBalance(type);
    }
    public String getAccountPassword(){
        System.out.print("Enter Password: ");
        String accountPassword = sc.next();
        return accountPassword;
    }
    public String accountConfirmPassword(){
        String accountPassword = getAccountPassword();
        System.out.print("Enter Confirm Password: ");
        String accountConfirmPassword = sc.next();
        if(accountConfirmPassword.equals(accountPassword)){
            return accountPassword;
        }
        System.out.println("Password not matched");
        return accountConfirmPassword();
    }
}
