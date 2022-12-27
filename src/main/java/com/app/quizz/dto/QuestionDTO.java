package com.app.quizz.dto;

import com.app.quizz.model.School;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;
@Getter
@Setter
public class QuestionDTO {
    private String id;
    private List<String> listAnswer;
    private String answerTrue;
    private String image;
    private String question;
    private boolean active;
    private boolean alreadyAnswer;
}
