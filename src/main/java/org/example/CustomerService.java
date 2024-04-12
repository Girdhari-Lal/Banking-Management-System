package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.Scanner;

public class CustomerService {
    Scanner sc = new Scanner(System.in);
    public Customer addCustomer(long cnic) {
        System.out.print("First Name: ");
        String firstName = sc.next();
        System.out.print("Last Name: ");
        String lastName = sc.next();
        sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        int age = getAge();
        String sex = getGender();
        String dateOfBirth = getDateOfBirth();
        System.out.print("Occupation: ");
        String occupation = sc.next();
        Customer customer = new Customer.CustomerBuilder().setCnic(cnic)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddress(address)
                .setAge(age)
                .setSex(sex)
                .setDateOfBirth(dateOfBirth)
                .setOccupation(occupation).build();
        return customer;
    }
    public int getAge(){
        System.out.print("Age: ");
        int age = sc.nextInt();
        if(age>=18){
            return age;
        }
        System.out.println("Your are not adult");
        return getAge();
    }
    public String getDateOfBirth(){
        Date d=new Date();
        int currentYear=d.getYear()+1900;
        System.out.print("Please enter the Date of Birth in the format (YYYY-MM-DD): ");
        String dateOfBirth = sc.next();
        String stringYear = dateOfBirth.substring(0,4);
        boolean isValid = false;
        try {
            int year = Integer.parseInt(stringYear);
            char sign = dateOfBirth.charAt(4);
            char sign2 = dateOfBirth.charAt(7);
            String stringMonth = dateOfBirth.substring(5, 7);
            int month = Integer.parseInt(stringMonth);
            String stringDay = dateOfBirth.substring(8, 10);
            int day = Integer.parseInt(stringDay);
            if((year>=1950 && year<currentYear) && sign=='-' && sign2 =='-'){
                int leapYear = year%4;
                if(leapYear==0 && month==2 && day<30){
                    isValid = true;
                }else if(leapYear!=0 && month==2 && day<29){
                    isValid = true;
                }else if(leapYear!=0 && month==2 && day>29){
                    System.out.println("invalid input month day");
                }else if((month==4 || month==6 || month==9 || month==11) && day<31){
                    isValid = true;
                }else if((month==4 || month==6 || month==9 || month==11) && day>=31){
                    System.out.println("invalid input month day");
                }else if((month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) && day<32){
                    isValid = true;
                }else if((month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) && day>=32){
                    isValid = true;
                } else if(month>12) {
                    System.out.println("invalid input month");
                }
            } else{
                System.out.println("invalid input year");
            }
        }catch (NumberFormatException e){
            System.out.println(e);
        }
        if (isValid){
            return dateOfBirth;
        }
        System.out.println("Enter again valid date of birth");
        return getDateOfBirth();
    }
    public String getGender(){
        System.out.print("Enter Gender: ");
        String sex = sc.next().toUpperCase();
        if(sex.equals("MALE") || sex.equals("FEMALE") || sex.equals("TRANSGENDER")){
            return sex;
        }
        System.out.println("Enter valid gender");
        return getGender();
    }
    public void setCustomerInfo(Session session, Customer customer){
        AccountService accountService = new AccountService();
        Transaction tx = session.beginTransaction();
        session.save(customer);
        System.out.println("Detail add Successfully!");
        tx.commit();
    }
    public void setAccountInfo(Session session, Customer customer, Account account){
        AccountService accountService = new AccountService();
        Transaction tx = session.beginTransaction();
        account.setCustomer(customer);
        session.save(account);
        System.out.println("Account Opened Successfully!");
        tx.commit();
    }
    public Customer findCustomerByCnic(Session session){
        Customer customer = null;
        System.out.println("Enter Your Details:");
        System.out.print("Enter your CNIC without -: ");
        long cnic = sc.nextLong();
        if(String.valueOf(cnic).length()>=13) {
            String query = "FROM Customer WHERE cnic = :cnic";
            Query q = session.createQuery(query);
            q.setParameter("cnic", cnic);
            Object result = q.uniqueResult();
            customer = (Customer) result;
            if (customer == null) {
                customer = addCustomer(cnic);
            }
        }else {
            System.out.println("Enter valid CNIC again");
            return findCustomerByCnic(session);
        }
        return customer;
    }
//    public void updateInfo(Scanner sc,Session session) {
//            Transaction tx = session.beginTransaction();
//            AccountService accountService = new AccountService();
//            int accountId = accountService.inputAccountId();
//            String accountPassword = accountService.inputAccountPassword();
//            System.out.print("""
//                Select Information to Update:
//                1. First name
//                2. Last name
//                3. Address
//                4. Occupation
//                Enter Selection:\s""");
//            int num = sc.nextInt();
//            String query = "SELECT a.customer FROM Account a WHERE a.id=:id and a.password = :password  and a.isOpen=true";
//            Query q = session.createQuery(query);
//            q.setParameter("id",accountId);
//            q.setParameter("password", accountPassword);
//            Object result = q.uniqueResult();
//            Customer customer = (Customer) result;
//        //int getAccountId = customer.getId();
//        //customer = (Customer) session.get(Customer.class, getAccountId);
//        Customer builder = null;
//        if (num==1){
//            System.out.print("Enter first name: ");
//            String firstName = sc.next();
//            builder = new Customer.CustomerBuilder(customer)
//                    .setFirstName(firstName)
//                    .build();
//            System.out.println("First name Updated Successfully!");
//        } else if(num==2){
//            System.out.print("Enter last name: ");
//            String lastName = sc.next();
//            builder = new Customer.CustomerBuilder(customer)
//                    .setLastName(lastName)
//                    .build();
//            System.out.println("Last name Updated Successfully!");
//        } else if(num==3){
//            System.out.print("Enter Address: ");
//            sc.nextLine();
//            String address = sc.nextLine();
//            builder = new Customer.CustomerBuilder(customer)
//                    .setAddress(address)
//                    .build();
//            System.out.println("Address Updated Successfully!");
//        } else if (num==4) {
//            System.out.print("Enter Occupation: ");
//            String occupation = sc.next();
//            builder = new Customer.CustomerBuilder(customer)
//                    .setOccupation(occupation)
//                    .build();
//            System.out.println("Occupation Updated Successfully!");
//        }else {
//            System.out.println("Error");
//        }
//        session.update(builder);
//        tx.commit();
//    }
    public void updateInfo(Scanner sc,Session session) {
        Transaction tx = session.beginTransaction();
        AccountService accountService = new AccountService();
        int accountId = accountService.getAccountId();
        String accountPassword = accountService.getAccountPassword();
        accountService.loginAccount(session, accountId, accountPassword);
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
            query = "update customer c, account a set c.first_name=:name where a.id="+accountId+" and a.password="+accountPassword+"  and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("name",firstName);
        } else if(num==2){
            System.out.print("Enter last name: ");
            String lastName = sc.next();
            query = "update customer c, account a set c.last_name=:name where a.id="+accountId+" and a.password="+accountPassword+" and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("name",lastName);
        } else if(num==3){
            System.out.print("Enter Address: ");
            sc.nextLine();
            String address = sc.nextLine();
            query = "update customer c, account a set c.address=:address where a.id="+accountId+" and a.password="+accountPassword+" and a.is_open=true";
            q = session.createSQLQuery(query);
            q.setParameter("address",address);
        } else if (num==4) {
            System.out.print("Enter Occupation: ");
            String occupation = sc.next();
            query = "update customer c, account a set c.occupation=:occupation where a.id="+accountId+" and a.password="+accountPassword+" and a.is_open=true";
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
