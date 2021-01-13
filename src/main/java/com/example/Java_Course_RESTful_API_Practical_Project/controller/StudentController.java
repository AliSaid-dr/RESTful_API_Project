package com.example.Java_Course_RESTful_API_Practical_Project.controller;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Student;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
@RestController
@RequestMapping(value = "students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Set<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping(value = "/facultyNumber/{facultyNumber}")
    public ResponseEntity<Student> findByFacultyNumber(@PathVariable String facultyNumber) {
        return ResponseEntity.ok(studentService.findByFacultyNumber(facultyNumber));
    }

    @PostMapping
    public ResponseEntity<Student> save(@RequestBody Student student) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.save(student));
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(student));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/facultyNumber/{facultyNumber}")
    public ResponseEntity<HttpStatus> deleteByFacultyNumber(@PathVariable String facultyNumber) {
        studentService.deleteByFacultyNumber(facultyNumber);
        return ResponseEntity.ok().build();
    }
}
