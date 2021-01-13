package com.example.Java_Course_RESTful_API_Practical_Project.service;

import com.example.Java_Course_RESTful_API_Practical_Project.exception.DuplicateRecordException;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Student;
import com.example.Java_Course_RESTful_API_Practical_Project.repository.StudentRepository;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.FacultyServiceImpl;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.SpecialityServiceImpl;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl.StudentServiceImpl;
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
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SpecialityServiceImpl specialityService;

    @Mock
    private FacultyServiceImpl facultyService;

    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentServiceImpl(studentRepository, specialityService, facultyService);
    }

    @Test
    public void findByFacultyNumberExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentService.findByFacultyNumber(null));
    }

    @Test
    public void findByFacultyNumberExpectSuccess() {
        Student student = Student.builder().facultyNumber("19621698").build();
        when(studentRepository.findByFacultyNumber(eq(student.getFacultyNumber())))
                .thenReturn(java.util.Optional.of(student));
        Student actual = studentService.findByFacultyNumber(student.getFacultyNumber());

        assertThat(actual, is(notNullValue()));
        assertEquals(actual, student);
    }

    @Test
    public void saveExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentService.save(null));
    }

    @Test
    public void saveExpectDuplicateRecordException() {
        Student student = Student.builder().firstName("Ali").build();
        when(studentRepository.save(eq(student)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> studentService.save(student));
    }

    @Test
    public void saveExpectSuccess() {
        Speciality speciality = Speciality.builder().speciality("SIT").build();
        Faculty faculty = Faculty.builder().faculty("FITA").build();
        Student student = Student.builder()
                .firstName("Ali")
                .lastName("Ali")
                .facultyNumber("19621698")
                .speciality(speciality)
                .faculty(faculty)
                .build();

        when(studentRepository.save(eq(student)))
                .thenReturn(student);
        Student actual = studentService.save(student);

        assertThat(actual, is(notNullValue()));
        assertEquals(actual, student);
    }

    @Test
    public void deleteByIdExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentService.deleteById(null));
    }

    @Test
    public void deleteByIdExpectSuccess() {
        Long deleteId = 1L;
        studentService.deleteById(deleteId);
        verify(studentRepository, times(1)).deleteById(deleteId);
    }

    @Test
    public void deleteByFacultyNumberExpectNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentService.deleteByFacultyNumber(null));
    }

    @Test
    public void deleteByFacultyNumberExpectSuccess() {
        String deleteFacultyNumber = "19621698";
        studentService.deleteByFacultyNumber(deleteFacultyNumber);
        verify(studentRepository, times(1)).deleteByFacultyNumber(deleteFacultyNumber);
    }
}