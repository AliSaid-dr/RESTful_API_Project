package com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;

import java.util.Set;

public interface SpecialityService {

    Set<Speciality> findAll();

    Speciality findById(Long id);

    Speciality findBySpeciality(String speciality);

    Speciality save(Speciality speciality);

    Speciality update(Speciality speciality);

    void deleteById(Long id);

    void deleteBySpeciality(String speciality);
}
