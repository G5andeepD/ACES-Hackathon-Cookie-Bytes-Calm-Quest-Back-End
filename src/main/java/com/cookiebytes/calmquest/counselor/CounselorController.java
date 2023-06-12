package com.cookiebytes.calmquest.counselor;

import com.cookiebytes.calmquest.student.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/counselor")
public class CounselorController {

    private final CounselorService counselorService;

    public CounselorController(CounselorService counselorService) {
        this.counselorService = counselorService;
    }

    @GetMapping
    public ResponseEntity<List<Counselor>> getAllStudents() {
        List<Counselor> students = counselorService.getAllCounselors();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}/getyourstudents")
    public ResponseEntity<List<Student>> getStudentsByCounselorId(@PathVariable long id){
        List<Student> students = counselorService.getStudentsByCounselorId(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Counselor> getCounselorById(@PathVariable long id) {
        Optional<Counselor> student = counselorService.getCounselorById(id);
        if (student != null) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }


    @PostMapping
    public ResponseEntity<Counselor> createStudent(@RequestBody Counselor counselor) {
        Counselor createdStudent = counselorService.createCounselor(counselor);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Counselor> updateStudent(@PathVariable long id, @RequestBody Counselor student) {

        Counselor updatedStudent = counselorService.updateCounselorById(student,id);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        boolean deleted = counselorService.deleteCounselorById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
