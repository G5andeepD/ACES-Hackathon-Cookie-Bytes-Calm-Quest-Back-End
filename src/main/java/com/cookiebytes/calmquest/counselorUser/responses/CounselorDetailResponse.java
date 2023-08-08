package com.cookiebytes.calmquest.counselorUser.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorDetailResponse {
    private String firstname;
    private String lastname;

    private String workplace;

    private String contactNumber;


}
