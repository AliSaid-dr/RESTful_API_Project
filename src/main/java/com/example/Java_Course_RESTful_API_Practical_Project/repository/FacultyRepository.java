package com.example.Java_Course_RESTful_API_Practical_Project.repository;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByFaculty(String faculty);

}
