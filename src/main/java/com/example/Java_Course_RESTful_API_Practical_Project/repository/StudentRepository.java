package com.example.Java_Course_RESTful_API_Practical_Project.repository;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByFacultyNumber(String facultyNumber);

    void deleteByFacultyNumber(String facultyNumber);

}
