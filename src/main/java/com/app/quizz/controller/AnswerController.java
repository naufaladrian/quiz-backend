package com.app.quizz.controller;

import com.app.quizz.dto.AnswerDTO;
import com.app.quizz.model.Materi;
import com.app.quizz.model.Score;
import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/question")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{materi-id}/answer")
    public CommonResponse<Score> answerQuest(@PathVariable("materi-id") String materiId, @RequestBody List<AnswerDTO> answerDTO){
        return CommonResponseGenerator.successResponse(answerService.answerQuestion(materiId, answerDTO));
    }
}
