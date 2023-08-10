package com.cookiebytes.calmquest.ml;

import com.cookiebytes.calmquest.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTextEmotionRepository extends JpaRepository<StudentTextEmotion,Long> {
    List<StudentTextEmotion> getStudentTextEmotionsByStudent(Student student);
}
