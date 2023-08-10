package com.cookiebytes.calmquest.studentUser;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.ml.EmotionResponseDTO;
import com.cookiebytes.calmquest.ml.TextSentimentRequest;
import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.student.StudentService;
import com.cookiebytes.calmquest.studentUser.responses.StudentAppointmentResponse;
import com.cookiebytes.calmquest.studentUser.responses.StudentCounselorResponse;
import com.cookiebytes.calmquest.studentUser.responses.StudentDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/v1/student")
public class StudentUserController {

    private final StudentUserService studentUserService;

    private  final StudentService studentService;

    public StudentUserController(StudentUserService studentUserService, StudentService studentService) {
        this.studentUserService = studentUserService;
        this.studentService = studentService;
    }
    @GetMapping()
    public ResponseEntity<StudentDetailResponse> getDetails(){
        return ResponseEntity.ok(studentUserService.getDetails());
    }
    @GetMapping("/counselor")
    public ResponseEntity<StudentCounselorResponse> getCounselor(){
        return ResponseEntity.ok(studentUserService.getCounselor());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<StudentAppointmentResponse>> getAllAppointments() {
        List<StudentAppointmentResponse> appointments = studentUserService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }



    @PostMapping("/appointments/create")
    public ResponseEntity<StudentAppointmentResponse> createAppointment(@RequestBody StudentAppointmentRequest studentAppointmentRequest) {
        StudentAppointmentResponse createdAppointment = studentUserService.createAppointment(studentAppointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<StudentAppointmentResponse> updateAppointment(
            @PathVariable("id") int id,
            @RequestBody Appointment appointment
    ) {
        StudentAppointmentResponse updatedAppointment = studentUserService.updateAppointment(id, appointment);
        if (updatedAppointment != null) {
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") int id) {
        boolean deleted = studentUserService.deleteAppointment(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Alerting

    @PutMapping("/alert/{regNo}")
    public ResponseEntity<Void> alertByRegNo(@PathVariable String regNo ){
        Student updatedStudent = studentService.increaseAlertCountByRegNo(regNo);
        if (updatedStudent != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //AI Data

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,path = "/ml/image")
    public void uploadImage(@RequestParam("face") MultipartFile imageFile){
        studentUserService.analyzeImage(imageFile);
    }

    @PostMapping("/ml/text")

    public void uploadText(@RequestBody TextSentimentRequest textSentimentRequest){
        studentUserService.analyzeText(textSentimentRequest);
    }



}
