package com.cookiebytes.calmquest.studentUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAppointmentRequest {


    private LocalDateTime scheduledDateTime;

    private String type;

    private String venue;
}