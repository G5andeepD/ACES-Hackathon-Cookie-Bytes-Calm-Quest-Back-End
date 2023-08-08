package com.cookiebytes.calmquest.studentUser.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailResponse {
    private int studentId;

    private String firstname;

    private String lastname;

    private String studentRegistrationNumber;

    private String faculty;

    private String university;

    private String contactNumber;

    private int alertCount;
}
