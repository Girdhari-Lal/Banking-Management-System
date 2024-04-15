package org.example;

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
        int Type = sc.nextInt();
        AccountType accountType;
        switch (Type) {
            case 1:
                accountType = AccountType.BASIC;
                break;
            case 2:
                accountType = AccountType.SAVING;
                break;
            case 3:
                accountType = AccountType.CURRENT;
                break;
            default:
                System.out.println("Invalid account type");
                return getAccountType();
        }
        return accountType;
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
    public String confirmPassword(){
        String accountPassword = getAccountPassword();
        System.out.print("Enter Confirm Password: ");
        String accountConfirmPassword = sc.next();
        if(accountConfirmPassword.equals(accountPassword)){
            return accountPassword;
        }
        System.out.println("Password not matched");
        return confirmPassword();
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
        if(currency.equals("USD") || currency.equals("EUR") || currency.equals("PKR" ) || currency.equals("YEN")){
            return CurrencyType.valueOf(currency);
        } else {
            System.out.println("Invalid currency");
            return getCurrency();
        }
    }
    public float getBalance(AccountType type){
        System.out.print("Enter amount to Deposit: ");
        float balance = sc.nextFloat();
        if ((type == AccountType.BASIC && balance >= 1000) || (type == AccountType.SAVING && balance>= 5000) || (type == AccountType.CURRENT && balance>=0)) {
            return balance;
        }
        System.out.println("Invalid amount of balance");
        return getBalance(type);
    }
}
