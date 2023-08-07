package com.cookiebytes.calmquest.counselor;

import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.user.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/counselor")
public class CounselorController {

    private final CounselorService counselorService;
    private final PasswordEncoder passwordEncoder;

    public CounselorController(CounselorService counselorService, PasswordEncoder passwordEncoder) {
        this.counselorService = counselorService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Counselor>> getAllStudents() {
        List<Counselor> students = counselorService.getAllCounselors();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}/getyourstudents")
    public ResponseEntity<List<Student>> getStudentsByCounselorId(@PathVariable int id) {
        List<Student> students = counselorService.getStudentsByCounselorId(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Counselor> getCounselorById(@PathVariable int id) {
        Optional<Counselor> student = counselorService.getCounselorById(id);
        if (student != null) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }


    @PostMapping
    public ResponseEntity<Counselor> createCounselor(@RequestBody CounselorCreateRequest request) {


        Counselor newCounselor = Counselor.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.COUNSELOR)
                .email(request.getEmail())
                .workplace(request.getWorkplace())
                .build();
        Counselor createCounselor = counselorService.createCounselor(newCounselor);
        return new ResponseEntity<>(createCounselor, HttpStatus.CREATED);

}

    @PutMapping("/{id}")
    public ResponseEntity<Counselor> updateStudent(@PathVariable int id, @RequestBody Counselor student) {

        Counselor updatedStudent = counselorService.updateCounselorById(student,id);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        boolean deleted = counselorService.deleteCounselorById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
