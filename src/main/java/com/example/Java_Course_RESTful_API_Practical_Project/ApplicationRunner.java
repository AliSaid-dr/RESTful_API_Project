package com.example.Java_Course_RESTful_API_Practical_Project;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.FacultyServiceImpl;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.SpecialityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private SpecialityServiceImpl specialityService;
    private FacultyServiceImpl facultyService;

    @Autowired
    public ApplicationRunner(SpecialityServiceImpl specialityService, FacultyServiceImpl facultyService) {
        this.specialityService = specialityService;
        this.facultyService = facultyService;
    }

    @Override
    public void run(String... args) throws Exception {
        specialityService.save(Speciality.builder().speciality("SIT").build());
        specialityService.save(Speciality.builder().speciality("Industrial design").build());
        facultyService.save(Faculty.builder().faculty("FITA").build());
        facultyService.save(Faculty.builder().faculty("KF").build());
    }
}
