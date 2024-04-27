package org.example.service;

import org.example.constant.GenderType;

import java.time.Year;
import java.util.Date;
import java.util.Scanner;

public class CustomerInputService {
    Scanner sc = new Scanner(System.in);
    public GenderType getGender(){
        System.out.print("Enter Gender: ");
        String sex = sc.next().toUpperCase();
        try {
            return GenderType.valueOf(sex);
        }catch (Exception e) {
            System.out.println("Enter again valid gender");
        }
        return getGender();
    }
    public String getDateOfBirth(){
        Year thisYear = Year.now();
        int currentYear= thisYear.getValue();
        System.out.print("Please enter the Date of Birth in the format (YYYY-MM-DD): ");
        String dateOfBirth = sc.next();
        String stringYear = dateOfBirth.substring(0,4);
        boolean isValid = true;
        try {
            int year = Integer.parseInt(stringYear);
            char sign = dateOfBirth.charAt(4);
            char sign2 = dateOfBirth.charAt(7);
            String stringMonth = dateOfBirth.substring(5, 7);
            int month = Integer.parseInt(stringMonth);
            String stringDay = dateOfBirth.substring(8, 10);
            int day = Integer.parseInt(stringDay);
            if(sign=='-' && sign2 =='-'){
                int leapYear = year%4;
                if(leapYear==0 && month==2 && day>29){
                    System.out.println("In leap year month days can't greater than 29");
                    isValid = false;
                }else if(leapYear!=0 && month==2 && day>28){
                    System.out.println("In February month days can't greater than 29");
                    isValid = false;
                }else if((month==4 || month==6 || month==9 || month==11) && day>30){
                    System.out.println("In this month days can't greater than 30");
                    isValid = false;
                }else if((month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) && day>31){
                    System.out.println("In this month days can't greater than 30");
                    isValid = false;
                }else if(month>12) {
                    System.out.println("invalid input month");
                }
            } else{
                System.out.println("invalid input year");
            }
        }catch (NumberFormatException | StringIndexOutOfBoundsException n){
            isValid = false;
        }
        if (!isValid){
            System.out.println("Enter valid date of birth again example: 2000-01-01");
            return getDateOfBirth();
        }
        return dateOfBirth;
    }
}
