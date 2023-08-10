package com.cookiebytes.calmquest.ml;

import com.cookiebytes.calmquest.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentFaceEmotionRepository extends JpaRepository<StudentFaceEmotion,Long> {
    List<StudentFaceEmotion> getStudentFaceEmotionsByStudent(Student student);
}
