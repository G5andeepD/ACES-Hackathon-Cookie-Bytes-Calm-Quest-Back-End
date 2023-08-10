package com.cookiebytes.calmquest.studentUser;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.appointment.AppointmentRepository;
import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.counselor.CounselorRepository;
import com.cookiebytes.calmquest.ml.*;
import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.student.StudentRepository;
import com.cookiebytes.calmquest.studentUser.responses.StudentAppointmentResponse;
import com.cookiebytes.calmquest.studentUser.responses.StudentCounselorResponse;
import com.cookiebytes.calmquest.studentUser.responses.StudentDetailResponse;
import com.cookiebytes.calmquest.user.User;
import com.cookiebytes.calmquest.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentUserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CounselorRepository counselorRepository;

    private final AppointmentRepository appointmentRepository;

    private final StudentFaceEmotionRepository studentFaceEmotionRepository;

    private final StudentTextEmotionRepository studentTextEmotionRepository;

    private final MLClient mlClient;

    public StudentUserService(UserRepository userRepository,
                              StudentRepository studentRepository,
                              CounselorRepository counselorRepository,
                              AppointmentRepository appointmentRepository,
                              StudentFaceEmotionRepository studentFaceEmotionRepository,
                              StudentTextEmotionRepository studentTextEmotionRepository,
                              MLClient mlClient) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.counselorRepository = counselorRepository;
        this.appointmentRepository = appointmentRepository;
        this.studentFaceEmotionRepository = studentFaceEmotionRepository;
        this.studentTextEmotionRepository = studentTextEmotionRepository;
        this.mlClient = mlClient;
    }


    public StudentCounselorResponse getCounselor() {
        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).get();
        Counselor counselor = student.getCounselor();
        if (counselor==null){
            throw new IllegalArgumentException("Counselor Not Assigned Yet");
        }
        return counselorResponseMapper(counselor);
    }

    private StudentCounselorResponse counselorResponseMapper(Counselor counselor) {

        StudentCounselorResponse studentCounselorResponse = new StudentCounselorResponse() ;
        studentCounselorResponse.setFirstname(counselor.getFirstname());
        studentCounselorResponse.setLastname(counselor.getLastname());
        studentCounselorResponse.setWorkplace(counselor.getWorkplace());
        studentCounselorResponse.setContactNumber(counselor.getContactNumber());


        return studentCounselorResponse;

    }

    public StudentDetailResponse getDetails() {
        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).get();

        StudentDetailResponse studentDetailResponse = new StudentDetailResponse();
        studentDetailResponse.setFirstname(student.getFirstname());
        studentDetailResponse.setLastname(student.getLastname());
        studentDetailResponse.setStudentRegistrationNumber(student.getStudentRegistrationNumber());
        studentDetailResponse.setContactNumber(student.getContactNumber());
        studentDetailResponse.setAlertCount(student.getAlertCount());
        studentDetailResponse.setStudentId(student.getId());
        studentDetailResponse.setUniversity(student.getUniversity());
        studentDetailResponse.setFaculty(student.getFaculty());


        return studentDetailResponse;


    }

    public List<StudentAppointmentResponse> getAllAppointments() {

        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).get();
        List<Appointment> appointments =  appointmentRepository.getAppointmentsByStudent(student);
        List<StudentAppointmentResponse> appointmentResponses = new ArrayList<>();
        for(Appointment appointment:appointments){
            StudentAppointmentResponse studentAppointmentResponse
                    = appointmentResponseMapper(appointment);

            appointmentResponses.add(studentAppointmentResponse);
        }


        return appointmentResponses;

    }


    public StudentAppointmentResponse createAppointment(StudentAppointmentRequest studentAppointmentRequest) {

        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).get();

        Appointment appointment = new Appointment();



        appointment.setCounselor(student.getCounselor());
        appointment.setStudent(student);
        appointment.setType(studentAppointmentRequest.getType());
        appointment.setVenue(studentAppointmentRequest.getVenue());
        appointment.setPlacementDateTime(LocalDateTime.now());
        appointment.setScheduledDateTime(studentAppointmentRequest.getScheduledDateTime());


        Appointment savedAppointment =  appointmentRepository.save(appointment);
        return appointmentResponseMapper(savedAppointment);
    }

    //TODO : Updating This way is troublesome : Need a fix
    public StudentAppointmentResponse updateAppointment(int id, Appointment appointment) {

        User user = getCurrentUser();

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

        if (appointmentRepository.existsById(id)) {
            Appointment existingAppointment = appointmentRepository.findById(id).get();
            if(existingAppointment.getStudent().getId()!=user.getId())
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
    private StudentAppointmentResponse appointmentResponseMapper(Appointment appointment){
        StudentAppointmentResponse studentAppointmentResponse = new StudentAppointmentResponse();
        studentAppointmentResponse.setAppointmentId(appointment.getId());
        studentAppointmentResponse.setScheduledDateTime(appointment.getScheduledDateTime());
        studentAppointmentResponse.setVenue(appointment.getVenue());
        studentAppointmentResponse.setType(appointment.getType());


        return studentAppointmentResponse;
    }

    //AI ML Insights
    public void analyzeText(TextSentimentRequest textSentimentRequest) {
        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).get();
        StudentTextEmotion studentTextEmotion = mlClient.getFullEmotionText(textSentimentRequest);
        studentTextEmotion.setTime(LocalDateTime.now());
        studentTextEmotion.setStudent(student);
        studentTextEmotionRepository.save(studentTextEmotion);

    }

    public void analyzeImage(MultipartFile imageFile) {

        User user = getCurrentUser();
        Student student = studentRepository.findById(user.getId()).get();
        StudentFaceEmotion studentFaceEmotion = mlClient.getFullEmotion(imageFile);
        studentFaceEmotion.setTime(LocalDateTime.now());
        studentFaceEmotion.setStudent(student);
        studentFaceEmotionRepository.save(studentFaceEmotion);


    }

}
