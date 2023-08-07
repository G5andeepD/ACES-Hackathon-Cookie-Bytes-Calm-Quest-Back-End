package com.cookiebytes.calmquest.counselorUser;

import com.cookiebytes.calmquest.appointment.Appointment;
import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.student.Student;
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
    public ResponseEntity<Counselor> getDetails(){
        return ResponseEntity.ok(counselorUserService.getDetails());
    }
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAssignedStudents(){
        return ResponseEntity.ok(counselorUserService.getAssignedStudents());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = counselorUserService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{studentId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStudentId(@PathVariable("studentId") int id) {
        List<Appointment> appointments = counselorUserService.getAppointmentByStudentId(id);
        if (appointments != null) {
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/appointments/create")
    public ResponseEntity<Appointment> createAppointment(@RequestBody CounselorAppointmentRequest counselorAppointmentRequest) {
        Appointment createdAppointment = counselorUserService.createAppointment(counselorAppointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable("id") int id,
            @RequestBody Appointment appointment
    ) {
        Appointment updatedAppointment = counselorUserService.updateAppointment(id, appointment);
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

    //TODO
    //Create CounselorAppointmentResponse
    //Create CounselorUserResponse
    //Create CounselorStudentResponse
}
