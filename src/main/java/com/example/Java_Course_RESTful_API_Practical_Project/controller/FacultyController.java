package com.example.Java_Course_RESTful_API_Practical_Project.controller;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "faculties")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Set<Faculty>> findAll() {
        return ResponseEntity.ok(facultyService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Faculty> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findById(id));
    }

    @GetMapping(value = "/faculty/{faculty}")
    public ResponseEntity<Faculty> findByFaculty(@PathVariable String faculty) {
        return ResponseEntity.ok(facultyService.findByFaculty(faculty));
    }

    @PostMapping
    public ResponseEntity<Faculty> save(@RequestBody Faculty faculty) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facultyService.save(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.update(faculty));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        facultyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
