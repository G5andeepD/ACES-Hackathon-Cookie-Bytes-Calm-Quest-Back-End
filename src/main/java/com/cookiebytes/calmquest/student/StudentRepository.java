package com.cookiebytes.calmquest.student;

import com.cookiebytes.calmquest.counselor.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    public Student findStudentByEmail(String email);
    public Student findStudentByStudentRegistrationNumber(String regNo);
    public List<Student> findStudentsByUniversityAndFaculty(String university,String faculty);

    public List<Student> getStudentsByCounselor(Optional<Counselor> counselor);
}
