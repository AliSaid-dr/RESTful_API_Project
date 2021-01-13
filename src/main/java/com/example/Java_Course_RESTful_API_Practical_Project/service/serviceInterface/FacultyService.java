package com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;

import java.util.Set;

public interface FacultyService {

    Set<Faculty> findAll();

    Faculty findById(Long id);

    Faculty findByFaculty(String faculty);

    Faculty save(Faculty faculty);

    Faculty update(Faculty faculty);

    void deleteById(Long id);
}
