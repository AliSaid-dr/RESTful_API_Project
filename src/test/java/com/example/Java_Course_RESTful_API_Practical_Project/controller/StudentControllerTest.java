package com.example.Java_Course_RESTful_API_Practical_Project.controller;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Student;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest extends BaseControllerTest{

    @MockBean
    private StudentService studentService;

    @Test
    public void findAllEmpty() throws Exception {
        when(studentService.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(empty())));
    }

    @Test
    public void findAll() throws Exception {
        Student student = Student.builder().id(1L).firstName("Ali").lastName("Ali").facultyNumber("19621690").build();

        when(studentService.findAll()).thenReturn(Collections.singleton(student));

        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findByIdNull() throws Exception {
        when(studentService.findById(1L)).thenReturn(null);

        mvc.perform(get("/students/1")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        Student student = Student.builder().id(1L).firstName("Ali").lastName("Ali").facultyNumber("19621690").build();

        when(studentService.findById(1L)).thenReturn(student);

        mvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.facultyNumber", is("19621690")));
    }

    @Test
    public void findByFacultyNull() throws Exception {
        when(studentService.findByFacultyNumber("19621698")).thenReturn(null);

        mvc.perform(get("/students/facultyNumber/19621698")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findByFaculty() throws Exception {
        Student student = Student.builder().id(1L).facultyNumber("19621698").build();

        when(studentService.findByFacultyNumber("19621698")).thenReturn(student);

        mvc.perform(get("/students/facultyNumber/19621698"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.facultyNumber", is("19621698")));
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/students")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveExpectSuccess() throws Exception {
        Student request = Student.builder().facultyNumber("19621698").build();
        String requestJson = objectMapper.writeValueAsString(request);
        Student response = Student.builder().id(1L).facultyNumber(request.getFacultyNumber()).build();

        when(studentService.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);

        mvc.perform(post("/students")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void deleteById() throws Exception {
        mvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBySpeciality() throws Exception {
        mvc.perform(delete("/students/facultyNumber/19621698"))
                .andExpect(status().isOk());
    }
}