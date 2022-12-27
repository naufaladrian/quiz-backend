package com.app.quizz.controller;

import com.app.quizz.dto.MateriDTO;
import com.app.quizz.model.Materi;
import com.app.quizz.model.Question;
import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.MateriService;
import com.app.quizz.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/question")
public class QuestionController {
    private QuestionService questionService;
    private ModelMapper modelMapper;
    private MateriService materiService;

    @Autowired
    public QuestionController(QuestionService questionService, ModelMapper modelMapper, MateriService materiService) {
        this.questionService = questionService;
        this.modelMapper = modelMapper;
        this.materiService=materiService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{school_id}")
    public CommonResponse<Materi> createMateriAndQuestion(@PathVariable("school_id")String schoolId, @RequestBody MateriDTO materiDTO){
        return CommonResponseGenerator.successResponse(questionService.createMateri(schoolId, materiDTO));
    }

    @GetMapping("/materi")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public CommonResponse<List<Materi>> getAllMateri(){
        return CommonResponseGenerator.successResponse(materiService.getAllMateri());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{materi_id}/user")
    public CommonResponse<Materi> getAllQuestionByUser(@PathVariable("materi_id")String materiId){
        return CommonResponseGenerator.successResponse(questionService.getMateriByUser(materiId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{materi_id}/admin")
    public CommonResponse<Materi> getAllQuestionByAdmin(@PathVariable("materi_id")String materiId){
        return CommonResponseGenerator.successResponse(questionService.getMateriByAdmin(materiId));
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{question_id}")
    public CommonResponse<Question> getQuestionById(@PathVariable("question_id")String questionId){
        return CommonResponseGenerator.successResponse(questionService.getQuestionById(questionId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{materi_id}")
    public CommonResponse<Map<String, Boolean>> deleteMateri(@PathVariable("materi_id") String id){
        return CommonResponseGenerator.successResponse(questionService.deleteMateri(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{materi_id}")
    public CommonResponse<Materi> updateMateri(@PathVariable("materi_id") String id, @RequestBody MateriDTO materi){
        return CommonResponseGenerator.successResponse(questionService.updateMateri(id ,modelMapper.map(materi,Materi.class)));
    }
}
