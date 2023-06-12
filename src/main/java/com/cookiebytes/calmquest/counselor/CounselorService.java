package com.cookiebytes.calmquest.counselor;

import com.cookiebytes.calmquest.student.Student;
import com.cookiebytes.calmquest.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CounselorService {
private final CounselorRepository counselorRepository;
private final StudentRepository studentRepository;

    public CounselorService(CounselorRepository counselorRepository, StudentRepository studentRepository) {
        this.counselorRepository = counselorRepository;
        this.studentRepository = studentRepository;
    }


    public List<Counselor> getAllCounselors() {
        return counselorRepository.findAll();
    }

    public List<Student> getStudentsByCounselorId(long id) {
        return  studentRepository.getStudentsByCounselor(counselorRepository.findById(id));
    }

    public Optional<Counselor> getCounselorById(long id) {
        return counselorRepository.findById(id);

    }

    public Counselor createCounselor(Counselor counselor) {

        return counselorRepository.save(counselor);
    }

    public Counselor updateCounselorById(Counselor counselor, long id) {
        counselor.setId(id);
        return counselorRepository.save(counselor);
    }

    public boolean deleteCounselorById(long id) {
        counselorRepository.deleteById(id);
        return false;
    }
}
