package com.cookiebytes.calmquest.student;

import com.cookiebytes.calmquest.counselor.CounselorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CounselorRepository counselorRepository;

    public StudentService(StudentRepository studentRepository, CounselorRepository counselorRepository) {
        this.studentRepository = studentRepository;
        this.counselorRepository = counselorRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(int id){
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }
    public Student updateStudentById(Student student,int id){
        student.setId(id);
        return studentRepository.save(student);
    }
    public boolean deleteStudentById(int id){

        studentRepository.deleteById(id);
        return false;
    }

    public Optional<Student> getStudentByEmail(String email) {
        return Optional.ofNullable(studentRepository.findStudentByEmail(email));
    }

    public Optional<Student> getStudentByStudentRegNo(String regNo) {
        return Optional.ofNullable(studentRepository.findStudentByStudentRegistrationNumber(regNo));
    }
    public List<Student> getStudentsByUniversityAndFaculty(String university,String faculty){
        return studentRepository.findStudentsByUniversityAndFaculty(university,faculty);
    }

    public Student setStudentCounselor(int id, int counselorid) {
        Student student = studentRepository.findById(id).get();
        student.setCounselor(counselorRepository.findById(counselorid).get());
        studentRepository.save(student);
        return student;
    }

    public Student increaseAlertCountByRegNo(String regNo) {
        Student student = studentRepository.findStudentByStudentRegistrationNumber(regNo);
        student.setAlertCount(student.getAlertCount()+1);
        studentRepository.save(student);
        return student;
    }
}
