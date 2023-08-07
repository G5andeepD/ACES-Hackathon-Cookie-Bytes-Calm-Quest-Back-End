package com.cookiebytes.calmquest.appointment;

import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    List<Appointment> getAppointmentsByCounselor(Counselor counselor);

    List<Appointment> getAppointmentsByCounselorAndStudent(Counselor counselor, Student student);
}
