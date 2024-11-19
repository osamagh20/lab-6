package com.example.lab6.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message = "Enter your ID")
    @Size(min = 3,message = "Enter id above 2 character")
    private String ID;
    @NotEmpty(message = "Enter your name")
    @Size(min = 3,message = "Enter name above 4 character")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Enter name with just character")
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^05\\d{8}$",message = "Start with 05 and exactly 10 digits")
    private String phoneNumber;
    @NotNull(message = "Enter your age")
    @Positive
    @Min(value = 26,message = "Enter more than 25")
    private int age;
    @NotEmpty(message = "Enter the position")
    @Pattern(regexp = "^(supervisor|coordinator)$",message = "the position Must be supervisor or coordinator.")
    private String position;
    @AssertFalse
    private Boolean onLeave;
    @NotNull(message = "Enter the date")
    @PastOrPresent
    private Date hireDate;
    @NotNull
    @Positive
    private int annualLeave;
}
