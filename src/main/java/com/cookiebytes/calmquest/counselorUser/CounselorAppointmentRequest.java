package com.cookiebytes.calmquest.counselorUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorAppointmentRequest {

   private int studentId;

    private LocalDateTime scheduledDateTime;

    private String type;

    private String venue;
}
