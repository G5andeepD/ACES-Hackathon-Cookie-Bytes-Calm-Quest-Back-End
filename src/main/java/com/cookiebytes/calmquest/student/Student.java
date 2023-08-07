package com.cookiebytes.calmquest.student;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.user.User;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Student extends User {




    private String gender;

    @Column(unique = true)
    private String studentRegistrationNumber;

    private String faculty;

    private String university;


    private int alertCount;
    @ManyToOne
    @JoinColumn(name = "counselor_id")

    private Counselor counselor;
//
//    @OneToMany(mappedBy = "student")
//
//    private List<Appointment> appointments;



}
