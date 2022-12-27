package com.app.quizz.dto;

import com.app.quizz.model.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MateriDTO {
    private String description;
    private String name;
    private List<QuestionDTO> questions=new ArrayList<>();
    private String teacher;
}
