package com.cookiebytes.calmquest.counselorUser;

import com.cookiebytes.calmquest.counselor.Counselor;
import com.cookiebytes.calmquest.counselor.CounselorRepository;
import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.student.StudentRepository;
import com.cookiebytes.calmquest.user.User;
import com.cookiebytes.calmquest.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CounselorUserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CounselorRepository counselorRepository;

    public CounselorUserService(UserRepository userRepository,
                                StudentRepository studentRepository,
                                CounselorRepository counselorRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.counselorRepository = counselorRepository;
    }

    public List<Student> getAssignedStudents(){
        User user  = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        List<Student> assignedStudents = studentRepository.findStudentsByCounselor(counselor);
        return assignedStudents;

    }

    public Counselor getDetails() {
        User user  = getCurrentUser();
        Counselor counselor = counselorRepository.findById(user.getId()).get();
        return counselor;


    }


    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();
        return user;
    }


}
