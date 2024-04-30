package org.example.service;

import org.example.AgeCalculatorUtil;
import org.example.constant.GenderType;
import org.example.entity.Customer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Scanner;

public class CustomerInputService {
    Scanner sc = new Scanner(System.in);
    public Customer inputCustomerDetail(String cnic){
        System.out.print("First Name: ");
        String firstName = sc.next();
        System.out.print("Last Name: ");
        String lastName = sc.next();
        sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        GenderType sex = getGender();
        String validAge = AgeCalculatorUtil.calculateAge();
        System.out.print("Occupation: ");
        String occupation = sc.next();
        return new Customer.CustomerBuilder().setCnic(cnic)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddress(address)
                .setSex(sex)
                .setDateOfBirth(validAge)
                .setOccupation(occupation).build();
    }
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
    public String getCnic() {
        String cnic;
        do {
            System.out.print("Enter your 13 digit CNIC: ");
            cnic = sc.next();
            if (cnic.length() != 13) {
                System.out.println("Invalid CNIC, The length of CNIC should be equal to the 13 digit");
                System.out.println("Enter valid CNIC again");
            }
        }while (cnic.length() != 13) ;
        return cnic;
    }
    public String getDateOfBirth(){
        System.out.print("Please enter the Date of Birth in the format (YYYY-MM-DD): ");
        String dateOfBirth = sc.next();
        try {
            char sign = dateOfBirth.charAt(4);
            char sign2 = dateOfBirth.charAt(7);
            if(sign=='-' && sign2 =='-' && dateOfBirth.length()>9){
                return dateOfBirth;
            }
        }catch (NumberFormatException | StringIndexOutOfBoundsException n){
            System.out.println(n.getMessage());
        }
        System.out.println("Enter valid date of birth again example: 2000-01-01");
        return getDateOfBirth();
    }
}
