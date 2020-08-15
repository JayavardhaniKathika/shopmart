package com.project.shopmart.Web.dto;

import org.springframework.context.annotation.Bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;


public class UserRegistrationDto {

   // @Column(name="first_name")
    @NotEmpty
    private String firstName;

    //@Column(name="last_name")
    @NotEmpty
    private String lastName;

    @Id
    //@Column(name="email")
    @NotEmpty
    @Email
    private String email;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    @Column(name="user_id")
    private long userId;

   // @Column(name="mobile_number")
    @NotEmpty
    private String mobileNumber;

    //@Column(name="password")
    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    //@Column(name="address")
    @NotEmpty
    private String address;


    @AssertTrue
    private Boolean terms;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
