package com.cookiebytes.calmquest.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
