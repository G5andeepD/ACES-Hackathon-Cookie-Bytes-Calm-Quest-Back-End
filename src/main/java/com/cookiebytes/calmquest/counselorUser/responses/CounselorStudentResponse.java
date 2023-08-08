package com.cookiebytes.calmquest.counselorUser.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorStudentResponse {
    private int studentId;

    private String studentName;

    private String studentRegistrationNumber;

    private String faculty;

    private String university;

    private String contactNumber;

    private int alertCount;
}
