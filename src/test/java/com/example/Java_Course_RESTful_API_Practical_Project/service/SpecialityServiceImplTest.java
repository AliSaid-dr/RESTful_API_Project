package com.example.Java_Course_RESTful_API_Practical_Project.service;

import com.example.Java_Course_RESTful_API_Practical_Project.exception.DuplicateRecordException;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.repository.SpecialityRepository;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.SpecialityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceImplTest {

    @Mock
    private SpecialityRepository specialityRepository;

    private SpecialityServiceImpl specialityService;

    @BeforeEach
    public void setUp() {
        specialityService = new SpecialityServiceImpl(specialityRepository);
    }

    @Test
    public void findBySpecialityExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> specialityService.findBySpeciality(null));
    }

    @Test
    public void findBySpecialityExpectSuccess() {
        Speciality speciality = Speciality.builder().speciality("SIT").build();
        when(specialityRepository.findBySpeciality(eq(speciality.getSpeciality())))
                .thenReturn(java.util.Optional.of(speciality));
        Speciality actual = specialityService.findBySpeciality(speciality.getSpeciality());
        assertThat(actual, is(notNullValue()));
        assertEquals(speciality, actual);
    }

    @Test
    public void saveExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> specialityService.save(null));
    }

    @Test
    public void saveExpectDuplicateRecordException() {
        Speciality speciality = Speciality.builder().speciality("SIT").build();
        when(specialityRepository.save(eq(speciality)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> specialityService.save(speciality));
    }

    @Test
    public void saveExpectSuccess() {
        Speciality speciality = Speciality.builder().speciality("SIT").build();
        when(specialityRepository.save(eq(speciality)))
                .thenReturn(speciality);
        Speciality actual = specialityService.save(speciality);

        assertThat(actual, is(notNullValue()));
        assertEquals("SIT", actual.getSpeciality());
    }

    @Test
    public void deleteByIdExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> specialityService.deleteById(null));
    }

    @Test
    public void deleteByIdExpectSuccess() {
        Long deleteId = 1L;
        specialityService.deleteById(deleteId);
        verify(specialityRepository, times(1)).deleteById(deleteId);
    }

    @Test
    public void deleteBySpecialityExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> specialityService.deleteBySpeciality(null));
    }

    @Test
    public void deleteBySpecialityExpectSuccess() {
        String deleteSpeciality = "SIT";
        specialityService.deleteBySpeciality(deleteSpeciality);
        verify(specialityRepository, times(1)).deleteBySpeciality(deleteSpeciality);
    }
}