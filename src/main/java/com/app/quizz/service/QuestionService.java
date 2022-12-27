package com.app.quizz.service;

import com.app.quizz.dto.MateriDTO;
import com.app.quizz.model.Materi;
import com.app.quizz.model.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    Materi createMateri(String schoolId, MateriDTO materiDTO);

    Materi getMateriByUser(String materiId);
    Materi getMateriByAdmin(String materiId);
    Question getQuestionById(String questionId);
    Map<String, Boolean> deleteMateri(String materiId);
    Materi updateMateri(String id, Materi materi);
}