package com.example.Java_Course_RESTful_API_Practical_Project.controller;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.FacultyServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest extends BaseControllerTest {

    @MockBean
    private FacultyServiceImpl facultyService;

    @Test
    public void findAllEmpty() throws Exception {
        when(facultyService.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(empty())));
    }

    @Test
    public void findAllFull() throws Exception {
        Faculty faculty = Faculty.builder().id(1L).faculty("EF").build();

        when(facultyService.findAll()).thenReturn(Collections.singleton(faculty));

        mvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findByIdNull() throws Exception {
        when(facultyService.findById(1L)).thenReturn(null);

        mvc.perform(get("/faculties/1")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void findById() throws Exception {
        Faculty faculty = Faculty.builder().id(1L).faculty("FITA").build();

        when(facultyService.findById(1L)).thenReturn(faculty);

        mvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.faculty", is("FITA")));
    }

    @Test
    public void findByFacultyNull() throws Exception {
        when(facultyService.findByFaculty("FITA")).thenReturn(null);

        mvc.perform(get("/faculties/faculty/FITA")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void findByFaculty() throws Exception {
        Faculty faculty = Faculty.builder().id(1L).faculty("FITA").build();

        when(facultyService.findByFaculty("FITA")).thenReturn(faculty);

        mvc.perform(get("/faculties/faculty/FITA"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.faculty", is("FITA")));
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/faculties")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void saveExpectSuccess() throws Exception {
        Faculty request = Faculty.builder().faculty("EF").build();
        String requestJson = objectMapper.writeValueAsString(request);
        Faculty response = Faculty.builder().id(1L).faculty(request.getFaculty()).build();

        when(facultyService.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);

        mvc.perform(post("/faculties")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void deleteById() throws Exception {
        mvc.perform(delete("/faculties/1"))
                .andExpect(status().isOk());
    }
}