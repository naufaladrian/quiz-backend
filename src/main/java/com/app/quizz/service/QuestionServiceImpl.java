package com.app.quizz.service;

import com.app.quizz.dto.MateriDTO;
import com.app.quizz.dto.QuestionDTO;
import com.app.quizz.exception.NotFoundException;
import com.app.quizz.model.Materi;
import com.app.quizz.model.Question;
import com.app.quizz.model.School;
import com.app.quizz.repository.MateriRepository;
import com.app.quizz.repository.QuestionRepository;
import com.app.quizz.repository.SchoolRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private MateriRepository materiRepository;
    private SchoolRepository schoolRepository;
    private ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, MateriRepository materiRepository, SchoolRepository schoolRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.materiRepository = materiRepository;
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
    }

    private Question addQuestion(Question question, Materi materi) {
        question.setMateriId(materi);
        question.setCountUsed(0);
        String joinQuestion = String.join(",", question.getAnswerList());
        System.out.println(joinQuestion);
        question.setListAnswer(joinQuestion);
        return questionRepository.save(question);
    }

    @Override
    public Materi createMateri(String schoolId, MateriDTO materiDTO) {
        Materi materi = modelMapper.map(materiDTO, Materi.class);
        School findSchoolId = schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("School Id Not Found"));
        materi.setSchoolId(findSchoolId);
        materi.setTotalQuestion(materiDTO.getQuestions().size());
        materi.setActive(true);
        Materi saveMateri = materiRepository.save(materi);
        for (int i = 0; i < materi.getTotalQuestion(); i++) {
            addQuestion(modelMapper.map(materiDTO.getQuestions().get(i), Question.class), materi);
        }
        var question = questionRepository.findAllByMateriId(saveMateri);
        question.forEach(e -> {
            boolean findAnswerTrueInAnswerList
                    =e.getListAnswer()
                    .contains(e.getAnswerTrue());
            if(findAnswerTrueInAnswerList==false) throw new NotFoundException("Fill answerTrue field with answer in answerList");
            e.setAnswerList(List.of(e.getListAnswer().split(",")));
        });
        saveMateri.setQuestions(question);
        return saveMateri;
    }

    @Override
    public Materi getMateriByUser(String materiId) {
        Materi findMateri = materiRepository.findById(materiId).orElseThrow(() -> new NotFoundException("Id Not Found"));
        List<Question> findAllQuestionByMateri = questionRepository.findAllByMateriId(findMateri);
        findAllQuestionByMateri.forEach(e -> {
            e.setAnswerTrue("");
            e.setAnswerList(List.of(e.getListAnswer().split(",")));
        });
        findMateri.setQuestions(findAllQuestionByMateri);
        return findMateri;
    }

    @Override
    public Materi getMateriByAdmin(String materiId) {
        Materi findMateri = materiRepository.findById(materiId).orElseThrow(() -> new NotFoundException("Id Not Found"));
        List<Question> findAllQuestionByMateri = questionRepository.findAllByMateriId(findMateri);
        findAllQuestionByMateri.forEach(e -> {
            e.setAnswerList(List.of(e.getListAnswer().split(",")));
        });
        findMateri.setQuestions(findAllQuestionByMateri);
        return findMateri;
    }

    @Override
    public Question getQuestionById(String questionId) {
        Question questionById=questionRepository.findById(questionId).orElseThrow(()->new NotFoundException("Id Not Found"));
        questionById.setAnswerList(List.of(questionById.getListAnswer().split(",")));
        return questionById;
    }

    @Override
    public Map<String, Boolean> deleteMateri(String materiId) {
        Materi materiById =materiRepository.findById(materiId).orElseThrow(() -> new NotFoundException("Id Not Found"));
        materiRepository.delete(materiById);
        Map<String, Boolean> response=new HashMap<>();
        response.put("DELETED", Boolean.TRUE);
        return response;
    }


    @Override
    @Transactional
    public Materi updateMateri(String id, Materi materi){
        Materi updateDataMateri=materiRepository.findById(id).orElseThrow(()->new NotFoundException("id materi not found"));
        updateDataMateri.setDescription(materi.getDescription());
        updateDataMateri.setTeacher(materi.getTeacher());
        updateDataMateri.setName(materi.getName());
        materi.getQuestions().forEach(e-> {
            if(e.getId()!=null){
                Question questionId = questionRepository.findById(e.getId()).orElseThrow(()->new NotFoundException("id question not found"));
                questionId.setQuestion(e.getQuestion());
                questionId.setAnswerList(e.getAnswerList());
                questionId.setAnswerTrue(e.getAnswerTrue());
                questionId.setImage(e.getImage());
                questionRepository.save(questionId);
            }else {
                addQuestion(modelMapper.map(e,Question.class), updateDataMateri);            }

        });
        updateDataMateri.setTotalQuestion(questionRepository.findAllByMateriId(updateDataMateri).size());
        return materiRepository.save(updateDataMateri);
    }
}
