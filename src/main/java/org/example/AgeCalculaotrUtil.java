package org.example;

import org.example.service.CustomerInputService;

import java.time.LocalDate;
public class AgeCalculaotrUtil {
    public static String calculateAge(){
        CustomerInputService customerInputService = new CustomerInputService();
        String dateOfBirth = customerInputService.getDateOfBirth();
        LocalDate currentDate = LocalDate.now();
        int currentYear= currentDate.getYear();
        int todaydate = currentDate.getDayOfMonth();
        int birthYear = Integer.parseInt(dateOfBirth.substring(0,4));
        int birthMonth = Integer.parseInt(dateOfBirth.substring(5,7));
        int birthDay = Integer.parseInt(dateOfBirth.substring(8,10));
        int currentMonth = currentDate.getMonthValue();;
        int age = currentYear - birthYear;
        if(birthMonth>currentMonth || (birthMonth==currentMonth && birthDay>todaydate)){
            age--;
        }
        if(age>18){
            return dateOfBirth;
        }
        System.out.println("Must be 18 or older to open an account.");
        return calculateAge();
    }
}
