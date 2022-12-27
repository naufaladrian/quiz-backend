package com.app.quizz.service;

import com.app.quizz.dto.AnswerDTO;
import com.app.quizz.model.Answer;
import com.app.quizz.model.Materi;
import com.app.quizz.model.Score;
import com.app.quizz.model.User;

import java.util.List;

public interface AnswerService {
     Score answerQuestion(String materiId, List<AnswerDTO> answerDTO);
     List<Score> getAllScore();
     Score getScoreById(String id);

}
