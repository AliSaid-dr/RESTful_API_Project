package com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Student;

import java.util.Set;

public interface StudentService {

    Set<Student> findAll();

    Student findById(Long id);

    Student findByFacultyNumber(String facultyNumber);

    Student save(Student student);

    Student update(Student student);

    void deleteById(Long id);

    void deleteByFacultyNumber(String facultyNumber);
}
