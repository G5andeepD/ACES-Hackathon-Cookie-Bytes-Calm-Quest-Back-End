package com.cookiebytes.calmquest.counselorUser.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorAppointmentResponse {

    private int appointmentId;
    private String studentRegistrationNumber;
    private String studentName;

    private LocalDateTime scheduledDateTime;

    private String type;

    private String venue;
}
