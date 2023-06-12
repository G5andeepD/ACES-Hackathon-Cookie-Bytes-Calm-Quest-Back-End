package com.cookiebytes.calmquest.counselor;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.student.Student;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "counselor")
public class Counselor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private long Id;
    private String firstName;
    private String lastName;

    private String workplace;
    @OneToMany(mappedBy = "counselor")
    @Hidden()
    private List<Student> students;
    @OneToMany(mappedBy = "counselor")
    private List<Appointment> appointments;

}
