package com.app.quizz.service;

import com.app.quizz.exception.NotFoundException;
import com.app.quizz.model.School;
import com.app.quizz.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService{

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public List<School> getAllSchool() {
        return schoolRepository.findAll();
    }

    @Override
    public School createSchool(School school) {
        return schoolRepository.save(school);

    }

    @Override
    public List<School> getRandomSchool() {
        return schoolRepository.getRandomAllSchool();
    }

    @Override
    public School getSchoolById(String id) {
        return schoolRepository.findById(id).orElseThrow(()->new NotFoundException("id not found"));
    }

    @Override
    public List<School> getSchoolByName(String name) {
        return schoolRepository.findAllByNameIgnoreCaseContainingOrderByIdAsc(name);
    }
}
