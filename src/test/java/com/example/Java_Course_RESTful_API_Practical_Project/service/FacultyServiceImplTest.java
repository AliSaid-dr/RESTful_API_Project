package com.example.Java_Course_RESTful_API_Practical_Project.service;

import com.example.Java_Course_RESTful_API_Practical_Project.exception.DuplicateRecordException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.RecordNotFoundException;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.repository.FacultyRepository;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.FacultyServiceImpl;
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
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    private FacultyServiceImpl facultyService;

    @BeforeEach
    public void setUp() {
        facultyService = new FacultyServiceImpl(facultyRepository);
    }

    @Test
    public void findByFacultyExpectNullPointerException() {
        assertThrows(RecordNotFoundException.class, () -> facultyService.findByFaculty(null));
    }

    @Test
    public void findByFacultyExpectSuccess() {
        Faculty faculty = Faculty.builder().faculty("FITA").build();
        when(facultyRepository.findByFaculty(eq(faculty.getFaculty())))
                .thenReturn(java.util.Optional.of(faculty));
        Faculty actual = facultyService.findByFaculty(faculty.getFaculty());
        assertThat(actual, is(notNullValue()));
        assertEquals(actual, faculty);
    }

    @Test
    public void saveExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> facultyService.save(null));
    }

    @Test
    public void saveExpectDuplicateRecordException() {
        Faculty faculty = Faculty.builder().faculty("FITA").build();
        when(facultyRepository.save(eq(faculty)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> facultyService.save(faculty));
    }

    @Test
    public void saveExpectSuccess() {
        Faculty faculty = Faculty.builder().faculty("FITA").build();

        when(facultyRepository.save(eq(faculty)))
                .thenReturn(faculty);
        Faculty actual = facultyService.save(faculty);

        assertThat(actual, is(notNullValue()));
        assertEquals("FITA", actual.getFaculty());
    }

    @Test
    public void deleteByIdExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> facultyService.deleteById(null));
    }

    @Test
    public void deleteByIdExpectSuccess() {
        Long deleteId = 1L;
        facultyService.deleteById(deleteId);
        verify(facultyRepository, times(1)).deleteById(deleteId);
    }
}