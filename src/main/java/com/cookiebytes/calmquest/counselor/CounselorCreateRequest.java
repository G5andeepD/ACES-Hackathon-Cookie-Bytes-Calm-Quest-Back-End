package com.cookiebytes.calmquest.counselor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorCreateRequest {
    private String firstName;


    private String lastName;

    private String password;


    private String gender;


    private String email;

    private String workplace;



}
