package com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl;

import com.example.Java_Course_RESTful_API_Practical_Project.exception.BadRequestException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.DuplicateRecordException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.RecordNotFoundException;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Faculty;
import com.example.Java_Course_RESTful_API_Practical_Project.repository.FacultyRepository;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.FacultyService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final static String FACULTY_NOT_FOUND = "Faculty not found with this ";
    private final static String FACULTY_ALREADY_EXISTS = "Faculty already exists with this name:";
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Set<Faculty> findAll() {
        return new HashSet<>(facultyRepository.findAll());
    }

    @Override
    public Faculty findById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(FACULTY_NOT_FOUND + "id:" + id));
    }

    @Override
    public Faculty findByFaculty(String faculty) {
        return facultyRepository.findByFaculty(faculty)
                .orElseThrow(() -> new RecordNotFoundException(FACULTY_NOT_FOUND + "name:" + faculty));
    }

    @Override
    public Faculty save(@NonNull Faculty faculty) {
        try {
            return facultyRepository.save(faculty);
        } catch(DataIntegrityViolationException ex) {
            throw new DuplicateRecordException(FACULTY_ALREADY_EXISTS + faculty.getFaculty());
        }
    }

    @Override
    public Faculty update(Faculty faculty) {
        if(faculty.getId() == null) {
            throw new BadRequestException("The id should not be null!");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        facultyRepository.deleteById(id);
    }
}
