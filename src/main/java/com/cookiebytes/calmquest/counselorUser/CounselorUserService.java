package com.cookiebytes.calmquest.counselorUser;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.appointment.AppointmentRepository;
import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.counselor.CounselorRepository;
import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.student.StudentRepository;
import com.cookiebytes.calmquest.user.User;
import com.cookiebytes.calmquest.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CounselorUserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CounselorRepository counselorRepository;

    private final AppointmentRepository appointmentRepository;

    public CounselorUserService(UserRepository userRepository,
                                StudentRepository studentRepository,
                                CounselorRepository counselorRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.counselorRepository = counselorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Student> getAssignedStudents() {
        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        List<Student> assignedStudents = studentRepository.findStudentsByCounselor(counselor);
        return assignedStudents;

    }

    public Counselor getDetails() {
        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        return counselor;


    }

    public List<Appointment> getAllAppointments() {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        return appointmentRepository.getAppointmentsByCounselor(counselor);
    }

    public List<Appointment> getAppointmentByStudentId(int studentId) {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        Student student = studentRepository.findById(studentId).get();
        if (student.getCounselor().getId() != user.getId()) {
            throw new IllegalArgumentException("This Student is not assigned to you");
        }
        return appointmentRepository.getAppointmentsByCounselorAndStudent(counselor, student);
    }
    public Appointment createAppointment(CounselorAppointmentRequest counselorAppointmentRequest) {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        Student student = studentRepository.findById(counselorAppointmentRequest.getStudentId()).get();
        if (student.getCounselor().getId() != user.getId()) {
            throw new IllegalArgumentException("This Student is not assigned to you");
        }
        Appointment appointment = new Appointment();



        appointment.setCounselor(counselor);

        appointment.setStudent(student);
        appointment.setType(counselorAppointmentRequest.getType());
        appointment.setVenue(counselorAppointmentRequest.getVenue());

        appointment.setPlacementDateTime(LocalDateTime.now());

        appointment.setScheduledDateTime(counselorAppointmentRequest.getScheduledDateTime());
        return appointmentRepository.save(appointment);


    }
    public Appointment updateAppointment(int id, Appointment appointment) {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        if (appointmentRepository.existsById(id)) {
            Appointment existingAppointment = appointmentRepository.findById(id).get();
            if(existingAppointment.getCounselor().getId()!=user.getId())
                throw new IllegalArgumentException("This Appointment Does Not Belong to you");


            appointment.setId(id);
            return appointmentRepository.save(appointment);
        }
        return null;



    }

    public boolean deleteAppointment(int id) {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        if (appointmentRepository.existsById(id)) {
            Appointment existingAppointment = appointmentRepository.findById(id).get();
            if(existingAppointment.getCounselor().getId()!=user.getId())
                throw new IllegalArgumentException("This Appointment Does Not Belong to you");



            appointmentRepository.deleteById(id);
            return true;
        }
        return false;


    }




    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();
        return user;
    }



}

