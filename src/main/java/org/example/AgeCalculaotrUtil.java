package org.example;

import java.time.LocalDate;
public class AgeCalculaotrUtil {
    public static int calculateAge(String dateOfBirth){
        LocalDate currentdate = LocalDate.now();
        int currentYear= currentdate.getYear();
        int todaydate = currentdate.getDayOfMonth();
        int birthYear = Integer.parseInt(dateOfBirth.substring(0,4));
        int birthMonth = Integer.parseInt(dateOfBirth.substring(5,7));
        int birthDay = Integer.parseInt(dateOfBirth.substring(8,10));
        int currentMonth = currentdate.getMonthValue();;
        int age = currentYear - birthYear;
        if(birthMonth>currentMonth || (birthMonth==currentMonth && birthDay>todaydate)){
            age--;
        }
        if(age<18){
            System.out.println("Your are not adult");
            System.exit(0);
        }
        return age;
    }
}
