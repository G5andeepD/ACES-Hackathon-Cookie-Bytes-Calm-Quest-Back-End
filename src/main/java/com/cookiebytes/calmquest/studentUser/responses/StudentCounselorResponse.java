package com.cookiebytes.calmquest.studentUser.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCounselorResponse {

    private String firstname;
    private String lastname;

    private String workplace;

    private String contactNumber;
}
