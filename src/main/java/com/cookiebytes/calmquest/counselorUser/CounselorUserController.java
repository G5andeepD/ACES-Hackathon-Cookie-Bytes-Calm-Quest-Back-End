package com.cookiebytes.calmquest.counselorUser;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.counselorUser.responses.CounselorAppointmentResponse;
import com.cookiebytes.calmquest.counselorUser.responses.CounselorDetailResponse;
import com.cookiebytes.calmquest.counselorUser.responses.CounselorStudentResponse;

import com.cookiebytes.calmquest.ml.StudentFaceEmotion;
import com.cookiebytes.calmquest.ml.StudentTextEmotion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/counselor")
public class CounselorUserController {

    private final CounselorUserService counselorUserService;

    public CounselorUserController(CounselorUserService counselorUserService) {
        this.counselorUserService = counselorUserService;
    }
    @GetMapping()
    public ResponseEntity<CounselorDetailResponse> getDetails(){
        return ResponseEntity.ok(counselorUserService.getDetails());
    }
    @GetMapping("/students")
    public ResponseEntity<List<CounselorStudentResponse>> getAssignedStudents(){
        return ResponseEntity.ok(counselorUserService.getAssignedStudents());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<CounselorAppointmentResponse>> getAllAppointments() {
        List<CounselorAppointmentResponse> appointments = counselorUserService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{studentId}")
    public ResponseEntity<List<CounselorAppointmentResponse>> getAppointmentsByStudentId(@PathVariable("studentId") int id) {
        List<CounselorAppointmentResponse> appointments = counselorUserService.getAppointmentByStudentId(id);
        if (appointments != null) {
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/appointments/create")
    public ResponseEntity<CounselorAppointmentResponse> createAppointment(@RequestBody CounselorAppointmentRequest counselorAppointmentRequest) {
        CounselorAppointmentResponse createdAppointment = counselorUserService.createAppointment(counselorAppointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<CounselorAppointmentResponse> updateAppointment(
            @PathVariable("id") int id,
            @RequestBody Appointment appointment
    ) {
        CounselorAppointmentResponse updatedAppointment = counselorUserService.updateAppointment(id, appointment);
        if (updatedAppointment != null) {
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") int id) {
        boolean deleted = counselorUserService.deleteAppointment(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //AI Insights

    @GetMapping("/student/ml/face/{student_id}")
    public ResponseEntity<List<StudentFaceEmotion>>
        getFaceEmotionInsightsByStudentId(@PathVariable("student_id") int studentId){
        List<StudentFaceEmotion> studentFaceEmotions = counselorUserService.getFaceEmotionInsightsByStudentId(studentId);
        return new ResponseEntity<>(studentFaceEmotions, HttpStatus.OK);
    }

    @GetMapping("/student/ml/text/{student_id}")
    public ResponseEntity<List<StudentTextEmotion>> getEmotionInsightsByStudentId(@PathVariable("student_id") int studentId) {
        List<StudentTextEmotion> studentTextEmotionList = counselorUserService.getTextEmotionInsightsByStudentId(studentId);
        return new ResponseEntity<>(studentTextEmotionList, HttpStatus.OK);
    }


}
