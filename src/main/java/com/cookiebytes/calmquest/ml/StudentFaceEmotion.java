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
public class StudentFaceEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private double angry;
    private double disgust;
    private double fear;
    private double happy;
    private double sad;
    private double surprise;
    private double neutral;

    private LocalDateTime time;
}
