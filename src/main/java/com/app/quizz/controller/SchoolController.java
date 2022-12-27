package com.app.quizz.controller;

import com.app.quizz.dto.SchoolDTO;
import com.app.quizz.model.School;
import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/school")
public class SchoolController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SchoolService schoolService;
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CommonResponse<School> createSchool (@RequestBody SchoolDTO schoolDTO){
        return CommonResponseGenerator.successResponse(schoolService.createSchool(modelMapper.map(schoolDTO, School.class)));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<List<School>> getAllSchool (){
        return CommonResponseGenerator.successResponse(schoolService.getAllSchool());
    }

    @GetMapping("/random")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<List<School>> getAllRandomSchool (){
        return CommonResponseGenerator.successResponse(schoolService.getRandomSchool());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<School> getSchoolById (@PathVariable("id") String id){
        return CommonResponseGenerator.successResponse(schoolService.getSchoolById(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<List<School>> getSchoolByName (@RequestParam String name){
        return CommonResponseGenerator.successResponse(schoolService.getSchoolByName(name));
    }

}
