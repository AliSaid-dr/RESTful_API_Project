package com.example.Java_Course_RESTful_API_Practical_Project.controller;

import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.SpecialityService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpecialityController.class)
class SpecialityControllerTest extends BaseControllerTest {

    @MockBean
    private SpecialityService specialityService;

    @Test
    public void findAllEmpty() throws Exception {
        when(specialityService.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/specialities"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(empty())));
    }

    @Test
    public void findAll() throws Exception {
        Speciality speciality = Speciality.builder().id(1L).speciality("Industrial design").build();

        when(specialityService.findAll()).thenReturn(Collections.singleton(speciality));

        mvc.perform(get("/specialities"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findByIdNull() throws Exception {
        when(specialityService.findById(1L)).thenReturn(null);

        mvc.perform(get("/specialities/1")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        Speciality speciality = Speciality.builder().id(1L).speciality("SIT").build();

        when(specialityService.findById(1L)).thenReturn(speciality);

        mvc.perform(get("/specialities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.speciality", is("SIT")));
    }

    @Test
    public void findByFacultyNull() throws Exception {
        when(specialityService.findBySpeciality("Industrial design")).thenReturn(null);

        mvc.perform(get("/specialities/speciality/Industrial design")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findByFaculty() throws Exception {
        Speciality speciality = Speciality.builder().id(1L).speciality("Industrial design").build();

        when(specialityService.findBySpeciality("Industrial design")).thenReturn(speciality);

        mvc.perform(get("/specialities/speciality/Industrial design"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.speciality", is("Industrial design")));
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/specialities")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveExpectSuccess() throws Exception {
        Speciality request = Speciality.builder().speciality("SIT").build();
        String requestJson = objectMapper.writeValueAsString(request);
        Speciality response = Speciality.builder().id(1L).speciality(request.getSpeciality()).build();

        when(specialityService.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);

        mvc.perform(post("/specialities")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void deleteById() throws Exception {
        mvc.perform(delete("/specialities/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBySpeciality() throws Exception {
        mvc.perform(delete("/specialities/speciality/SIT"))
                .andExpect(status().isOk());
    }
}