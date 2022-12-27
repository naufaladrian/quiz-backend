package com.app.quizz.controller;

import com.app.quizz.model.Score;
import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/user-score")
public class ScoreController {
    @Autowired
    private AnswerService answerService;
    @GetMapping
    public CommonResponse<List<Score>> getAllScore(){
        return CommonResponseGenerator.successResponse(answerService.getAllScore());
    }
    @GetMapping("/{id}")
    public CommonResponse<Score> getScoreById(@PathVariable("id")String id){
        return CommonResponseGenerator.successResponse(answerService.getScoreById(id));
    }
}
