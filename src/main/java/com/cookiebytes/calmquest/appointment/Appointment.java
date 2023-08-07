package com.cookiebytes.calmquest.appointment;

import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.student.Student;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    @Hidden
    private Student student;
    @ManyToOne
    @JoinColumn(name="counselor_id")
    @Hidden
    private Counselor counselor;

    private LocalDateTime placementDateTime;

    private LocalDateTime scheduledDateTime;

    private String type;

    private String venue;
}
