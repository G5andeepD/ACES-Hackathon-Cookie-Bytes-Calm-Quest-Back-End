package com.cookiebytes.calmquest.studentUser.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentAppointmentResponse {
    private int appointmentId;

    private LocalDateTime scheduledDateTime;

    private String venue;
}
