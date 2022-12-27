package com.app.quizz.dto;

import com.app.quizz.model.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
    private String questionId;
    private String answer;
}
