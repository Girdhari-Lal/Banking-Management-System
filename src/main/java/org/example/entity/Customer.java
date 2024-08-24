package org.example.entity;

import org.example.constant.GenderType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.EnumType;
@Entity
public class Customer {
    public Customer(){}
    private Customer(CustomerBuilder builder){
        this.cnic = builder.cnic;
        this.occupation = builder.occupation;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.address = builder.address;
        this.sex = builder.sex;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(unique = true, name = "cnic", nullable = false)
    private String cnic;
    @Column(name = "occupation", nullable = false)
    private String occupation;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;
    @Column(name = "address", nullable = false)
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name="sex", nullable = false)
    private GenderType sex;
    public String getOccupation() {
        return occupation;
    }
    public String getLastName() {
        return lastName;
    }
    public GenderType getSex() {
        return sex;
    }
    public String getCnic() {
        return cnic;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getAddress() {
        return address;
    }

    public static class CustomerBuilder{
        private String cnic;
        private String occupation;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String address;
        private GenderType sex;
        public CustomerBuilder(Customer customer) {
            this.occupation = customer.getOccupation();
            this.firstName = customer.getFirstName();
            this.lastName = customer.getLastName();
            this.dateOfBirth = customer.getDateOfBirth();
            this.address = customer.getAddress();
            this.sex = customer.getSex();
            this.cnic = customer.getCnic();
        }
        public CustomerBuilder(){}
        public CustomerBuilder setOccupation(String occupation) {
           this.occupation = occupation;
            return this;
        }
        public CustomerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public CustomerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public CustomerBuilder setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        public CustomerBuilder setAddress(String address) {
            this.address = address;
            return this;
        }
        public CustomerBuilder setAge(int age) {
            return this;
        }
        public CustomerBuilder setSex(GenderType sex) {
            this.sex = sex;
            return this;
        }
        public CustomerBuilder setCnic(String cnic) {
            this.cnic = cnic;
            return this;
        }
        public Customer build(){
            return new Customer(this);
        }
    }
}
