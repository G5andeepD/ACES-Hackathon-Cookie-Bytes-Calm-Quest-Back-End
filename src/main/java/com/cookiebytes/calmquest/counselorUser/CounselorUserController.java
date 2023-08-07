package com.cookiebytes.calmquest.counselorUser;

import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.student.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/counselor/")
public class CounselorUserController {

    private final CounselorUserService counselorUserService;

    public CounselorUserController(CounselorUserService counselorUserService) {
        this.counselorUserService = counselorUserService;
    }
    @GetMapping()
    public ResponseEntity<Counselor> getDetails(){
        return ResponseEntity.ok(counselorUserService.getDetails());
    }
    @GetMapping("students")
    public ResponseEntity<List<Student>> getAssignedStudents(){
        return ResponseEntity.ok(counselorUserService.getAssignedStudents());
    }
}
