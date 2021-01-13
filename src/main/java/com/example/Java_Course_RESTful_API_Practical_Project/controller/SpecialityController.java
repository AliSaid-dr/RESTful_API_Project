package com.example.Java_Course_RESTful_API_Practical_Project.controller;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "specialities")
public class SpecialityController {

    private final SpecialityService specialityService;

    @Autowired
    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public ResponseEntity<Set<Speciality>> findAll() {
        return ResponseEntity.ok(specialityService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Speciality> findById(@PathVariable Long id) {
        return ResponseEntity.ok(specialityService.findById(id));
    }

    @GetMapping(value = "speciality/{speciality}")
    public ResponseEntity<Speciality> findBySpeciality(@PathVariable String speciality) {
        return ResponseEntity.ok(specialityService.findBySpeciality(speciality));
    }

    @PostMapping
    public ResponseEntity<Speciality> save(@RequestBody Speciality speciality) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(specialityService.save(speciality));
    }

    @PutMapping
    public ResponseEntity<Speciality> update(@RequestBody Speciality speciality) {
        return ResponseEntity.ok(specialityService.update(speciality));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        specialityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "speciality/{speciality}")
    public ResponseEntity<HttpStatus> deleteBySpeciality(@PathVariable String speciality) {
        specialityService.deleteBySpeciality(speciality);
        return ResponseEntity.ok().build();
    }
}
