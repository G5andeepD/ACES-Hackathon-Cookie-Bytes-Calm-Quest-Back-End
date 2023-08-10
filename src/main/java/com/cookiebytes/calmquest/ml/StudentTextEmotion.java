package com.cookiebytes.calmquest.ml;

import com.cookiebytes.calmquest.student.Student;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class StudentTextEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private double anger;
    private double anticipation;
    private double disgust;
    private double fear;
    private double joy;
    private double negative;
    private double positive;
    private double sadness;
    private double surprise;
    private double trust;

    private LocalDateTime time;
}
