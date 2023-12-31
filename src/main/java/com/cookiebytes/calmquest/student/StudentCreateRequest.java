package com.cookiebytes.calmquest.student;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {


    private String firstName;


    private String lastName;

    private String password;


    private String gender;


    private String email;


    private String studentRegistrationNumber;


    private String faculty;


    private String university;



}
