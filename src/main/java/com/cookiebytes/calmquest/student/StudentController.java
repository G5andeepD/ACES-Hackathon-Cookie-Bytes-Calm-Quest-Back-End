package com.cookiebytes.calmquest.student;


import com.cookiebytes.calmquest.user.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/student")
public class StudentController {

    private final StudentService studentService;

    private final PasswordEncoder passwordEncoder;


    public StudentController(StudentService studentService, PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("getby/{university}/{faculty}")
    public ResponseEntity<List<Student>> getStudentsByUniversityandFaculty(
            @PathVariable String university,
            @PathVariable String faculty) {
        List<Student> students = studentService.getStudentsByUniversityAndFaculty(university,faculty);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student != null) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        Optional<Student> student = studentService.getStudentByEmail(email);
        if (student != null) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("regNo/{regNo}")
    public ResponseEntity<Student> getStudentByRegNo(@PathVariable String regNo) {
        Optional<Student> student = studentService.getStudentByStudentRegNo(regNo);
        if (student != null) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentCreateRequest request) {


        Student newStudent = Student.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .email(request.getEmail())
                .studentRegistrationNumber(request.getStudentRegistrationNumber())
                .faculty(request.getFaculty())
                .university(request.getUniversity())
                .build();
        Student createdStudent = studentService.createStudent(newStudent);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudentById(student,id);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/{id}/{counselorid}")
    public ResponseEntity<Student> setStudentCounselor(@PathVariable int id, @PathVariable int counselorid){
        Student updatedStudent = studentService.setStudentCounselor(id,counselorid);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/alert/{regNo}")
    public ResponseEntity<Student> alertByFriend(@PathVariable String regNo ){
        Student updatedStudent = studentService.increaseAlertCountByRegNo(regNo);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id ) {
        boolean deleted = studentService.deleteStudentById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

