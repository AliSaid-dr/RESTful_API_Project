package com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl;

import com.example.Java_Course_RESTful_API_Practical_Project.exception.BadRequestException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.DuplicateRecordException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.RecordNotFoundException;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Student;
import com.example.Java_Course_RESTful_API_Practical_Project.repository.StudentRepository;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.FacultyService;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.SpecialityService;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.StudentService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final static String STUDENT_NOT_FOUND = "Student not found with this ";
    private final static String STUDENT_ALREADY_EXISTS = "Student already exists with this ";
    private final StudentRepository studentRepository;
    private final SpecialityService specialityService;
    private final FacultyService facultyService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, SpecialityService specialityService, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.specialityService = specialityService;
        this.facultyService = facultyService;
    }

    @Override
    public Set<Student> findAll() {
        return new HashSet<>(studentRepository.findAll());
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(STUDENT_NOT_FOUND + "id: " + id));
    }

    @Override
    public Student findByFacultyNumber(@NonNull String facultyNumber) {
        return studentRepository.findByFacultyNumber(facultyNumber)
                .orElseThrow(() -> new RecordNotFoundException(STUDENT_NOT_FOUND + "faculty number: " + facultyNumber));
    }

    @Override
    public Student save(@NonNull Student student) {
        try {
            Faculty facultyFita = facultyService.findByFaculty("FITA");
            Speciality specialitySIT = specialityService.findBySpeciality("SIT");
            student.setFaculty(facultyFita);
            student.setSpeciality(specialitySIT);
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException(STUDENT_ALREADY_EXISTS + "faculty number: " + student.getFacultyNumber());
        }
    }

    @Override
    public Student update(Student student) {
        if(student.getId() == null) {
            throw new BadRequestException("The id must not be null!");
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void deleteByFacultyNumber(@NonNull String facultyNumber) {
        studentRepository.deleteByFacultyNumber(facultyNumber);
    }
}
