package com.example.Java_Course_RESTful_API_Practical_Project.repository;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findBySpeciality(String speciality);

    void deleteBySpeciality(String speciality);
}
