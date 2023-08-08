package com.cookiebytes.calmquest.counselorUser;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.appointment.AppointmentRepository;
import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.counselor.CounselorRepository;
import com.cookiebytes.calmquest.counselorUser.responses.CounselorAppointmentResponse;
import com.cookiebytes.calmquest.counselorUser.responses.CounselorDetailResponse;
import com.cookiebytes.calmquest.counselorUser.responses.CounselorStudentResponse;
import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.student.StudentRepository;
import com.cookiebytes.calmquest.user.User;
import com.cookiebytes.calmquest.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<CounselorStudentResponse> getAssignedStudents() {
        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        List<Student> assignedStudents = studentRepository.findStudentsByCounselor(counselor);
        List<CounselorStudentResponse> studentResponses = new ArrayList<>();

        for(Student student:assignedStudents)
            studentResponses.add(studentResponseMapper(student));


        return studentResponses;

    }

    private CounselorStudentResponse studentResponseMapper(Student student) {

        CounselorStudentResponse counselorStudentResponse = new CounselorStudentResponse() ;
        counselorStudentResponse.setStudentId(student.getId());
        counselorStudentResponse.setStudentName(student.getFirstname() + " " + student.getLastname());
        counselorStudentResponse.setStudentRegistrationNumber(student.getStudentRegistrationNumber());
        counselorStudentResponse.setFaculty(student.getFaculty());
        counselorStudentResponse.setUniversity(student.getUniversity());
        counselorStudentResponse.setAlertCount(student.getAlertCount());
        counselorStudentResponse.setContactNumber(student.getContactNumber());


        return counselorStudentResponse;

    }

    public CounselorDetailResponse getDetails() {
        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();

        CounselorDetailResponse counselorDetailResponse = new CounselorDetailResponse();
        counselorDetailResponse.setFirstname(counselor.getFirstname());
        counselorDetailResponse.setLastname(counselor.getLastname());
        counselorDetailResponse.setWorkplace(counselor.getWorkplace());
        counselorDetailResponse.setContactNumber(counselor.getContactNumber());

        return counselorDetailResponse;


    }

    public List<CounselorAppointmentResponse> getAllAppointments() {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        List<Appointment> appointments =  appointmentRepository.getAppointmentsByCounselor(counselor);
        List<CounselorAppointmentResponse> appointmentResponses = new ArrayList<>();
        for(Appointment appointment:appointments){
            CounselorAppointmentResponse counselorAppointmentResponse
                    = appointmentResponseMapper(appointment);

            appointmentResponses.add(counselorAppointmentResponse);
        }


        return appointmentResponses;

    }

    public List<CounselorAppointmentResponse> getAppointmentByStudentId(int studentId) {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        Student student = studentRepository.findById(studentId).get();
        if (student.getCounselor().getId() != user.getId()) {
            throw new IllegalArgumentException("This Student is not assigned to you");
        }
        List<Appointment> appointments =  appointmentRepository
                                        .getAppointmentsByCounselorAndStudent(counselor, student);

        List<CounselorAppointmentResponse> appointmentResponses = new ArrayList<>();
        for(Appointment appointment:appointments){
            CounselorAppointmentResponse counselorAppointmentResponse
                    = appointmentResponseMapper(appointment);

            appointmentResponses.add(counselorAppointmentResponse);
        }


        return appointmentResponses;

    }
    public CounselorAppointmentResponse createAppointment(CounselorAppointmentRequest counselorAppointmentRequest) {

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


        Appointment savedAppointment =  appointmentRepository.save(appointment);
        return appointmentResponseMapper(savedAppointment);
    }
    public CounselorAppointmentResponse updateAppointment(int id, Appointment appointment) {

        User user = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        if (appointmentRepository.existsById(id)) {
            Appointment existingAppointment = appointmentRepository.findById(id).get();
            if(existingAppointment.getCounselor().getId()!=user.getId())
                throw new IllegalArgumentException("This Appointment Does Not Belong to you");


            appointment.setId(id);
            return appointmentResponseMapper(appointmentRepository.save(appointment));
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
    private CounselorAppointmentResponse appointmentResponseMapper(Appointment appointment){
        CounselorAppointmentResponse counselorAppointmentResponse = new CounselorAppointmentResponse();
        counselorAppointmentResponse.setAppointmentId(appointment.getId());
        counselorAppointmentResponse.setStudentName(appointment.getStudent().getFirstname());
        counselorAppointmentResponse.setStudentRegistrationNumber(appointment.getStudent().getStudentRegistrationNumber());
        counselorAppointmentResponse.setScheduledDateTime(appointment.getScheduledDateTime());
        counselorAppointmentResponse.setVenue(appointment.getVenue());

        return counselorAppointmentResponse;
    }



}

