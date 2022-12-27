package com.app.quizz.service;

import com.app.quizz.model.School;

import java.util.List;

public interface SchoolService {

    School createSchool(School school);
    List<School> getAllSchool();
    List<School> getRandomSchool();
    School getSchoolById(String id);
   List<School> getSchoolByName(String name);

}
