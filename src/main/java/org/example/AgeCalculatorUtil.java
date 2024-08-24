package org.example;

import org.example.service.CustomerInputService;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;

public class AgeCalculatorUtil {
    public static String calculateAge() {
        CustomerInputService customerInputService = new CustomerInputService();
        String dateOfBirth = customerInputService.getDateOfBirth();
        int birthYear = Integer.parseInt(dateOfBirth.substring(0, 4));
        int birthMonth = Integer.parseInt(dateOfBirth.substring(5, 7));
        int birthDay = Integer.parseInt(dateOfBirth.substring(8, 10));
        try {
            LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);
            int age = period.getYears();
            if(age>=18){
                return dateOfBirth;
            }else {
                System.out.println("Must be 18 or older to open an account.");
            }
        } catch (DateTimeException e){
            System.out.println(e.getMessage());
        }
        return calculateAge();
    }
}
