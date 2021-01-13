package com.example.Java_Course_RESTful_API_Practical_Project.service.serviceImpl;

import com.example.Java_Course_RESTful_API_Practical_Project.exception.BadRequestException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.DuplicateRecordException;
import com.example.Java_Course_RESTful_API_Practical_Project.exception.RecordNotFoundException;
import com.example.Java_Course_RESTful_API_Practical_Project.model.Speciality;
import com.example.Java_Course_RESTful_API_Practical_Project.repository.SpecialityRepository;
import com.example.Java_Course_RESTful_API_Practical_Project.service.serviceInterface.SpecialityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SpecialityServiceImpl implements SpecialityService {

    private final static String SPECIALITY_NOT_FOUND = "Speciality not found with this ";
    private final static String SPECIALITY_ALREADY_EXISTS = "Speciality already exists with this name:";
    private final SpecialityRepository specialityRepository;

    @Autowired
    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        return new HashSet<>(specialityRepository.findAll());
    }

    @Override
    public Speciality findById(Long id) {
        return specialityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(SPECIALITY_NOT_FOUND + "id: " + id));
    }

    @Override
    public Speciality findBySpeciality(@NonNull String speciality) {
        return specialityRepository.findBySpeciality(speciality)
                .orElseThrow(() -> new RecordNotFoundException(SPECIALITY_NOT_FOUND + "name " + speciality));
    }

    @Override
    public Speciality save(@NonNull Speciality speciality) {
        try {
            return specialityRepository.save(speciality);
        } catch(DataIntegrityViolationException ex) {
            throw new DuplicateRecordException(SPECIALITY_ALREADY_EXISTS + speciality.getSpeciality());
        }
    }

    @Override
    public Speciality update(Speciality speciality) {
        if(speciality.getId() == null) {
            throw new BadRequestException("The id should not be null");
        }
        return specialityRepository.save(speciality);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        specialityRepository.deleteById(id);
    }

    @Override
    public void deleteBySpeciality(@NonNull String speciality) {
        specialityRepository.deleteBySpeciality(speciality);
    }
}
