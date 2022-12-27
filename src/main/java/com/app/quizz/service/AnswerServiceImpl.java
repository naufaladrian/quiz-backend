package com.app.quizz.service;

import com.app.quizz.config.AuthenticationFacade;
import com.app.quizz.dto.AnswerDTO;
import com.app.quizz.exception.NotFoundException;
import com.app.quizz.model.*;
import com.app.quizz.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService{
    public static float correct=0;
    private QuestionRepository questionRepository;
    private MateriRepository materiRepository;
    private AnswerRepository answerRepository;
    private ModelMapper modelMapper;
    private ScoreRepository scoreRepository;
    private SchoolRepository schoolRepository;
    private AuthenticationFacade authenticationFacade;


    @Autowired
    public AnswerServiceImpl(SchoolRepository schoolRepository,QuestionRepository questionRepository, MateriRepository materiRepository, AnswerRepository answerRepository,ModelMapper modelMapper, ScoreRepository scoreRepository, AuthenticationFacade authenticationFacade) {
        this.schoolRepository =schoolRepository;
        this.questionRepository = questionRepository;
        this.materiRepository = materiRepository;
        this.answerRepository = answerRepository;
        this.modelMapper=modelMapper;
        this.scoreRepository=scoreRepository;
        this.authenticationFacade=authenticationFacade;
    }


    @Override
    @Transactional
    public Score answerQuestion(String materiId, List<AnswerDTO> answerDTO) {
        User getUser=authenticationFacade.getAuthentication();
        Materi findMateriId=materiRepository.findById(materiId).orElseThrow(()->new NotFoundException("Materi Id Not Found"));
        School school=findMateriId.getSchoolId();
        if(getUser.getSchoolId()==null||!(getUser.getSchoolId().getId().equals(school.getId()))) throw new NotFoundException("You Are Not Registered In This School");

        Score score=new Score();


        if(!(scoreRepository.findAllByMateriIdAndUserId(findMateriId, getUser).isEmpty())) throw new NotFoundException("You Already Answered");
        correct=0;
        answerDTO.forEach(e->{

            Answer answer=new Answer();
            Question question=questionRepository.findById(e.getQuestionId()).orElseThrow(()->new NotFoundException("id question not found"));
            boolean findAnswerInAnswerList
                    =List.of(question.getListAnswer().split(","))
                    .contains(e.getAnswer());

            if (findAnswerInAnswerList==false) throw new NotFoundException("Answer Not Found");
            if (e.getAnswer().equals(question.getAnswerTrue())){
                answer.setCorrect(true);
                correct++;
            }else {
                answer.setCorrect(false);
            }

            answer.setAnswer(e.getAnswer());
            answer.setQuestionId(question);
            answer.setMateriId(findMateriId);
            findMateriId.setAlreadyAnswer(true);
            answer.setUserId(getUser);
            answerRepository.save(answer);

        });
        float point= 100/findMateriId.getTotalQuestion();
        score.setPoint(correct);
        if(correct== findMateriId.getTotalQuestion()){
            score.setScore(100);
        }else {
            score.setScore(point*correct);
        }
        score.setSchoolId(findMateriId.getSchoolId());
        score.setMateriId(findMateriId);
        score.setUserId(getUser);

        ;

        return scoreRepository.save(score);
    }

    @Override
    public List<Score> getAllScore(){
     return scoreRepository.findAllByUserId(authenticationFacade.getAuthentication());
    }

    @Override
    public Score getScoreById(String id) {
        return scoreRepository.findByIdAndUserId(id, authenticationFacade.getAuthentication()).orElseThrow(()->new NotFoundException("score id or user id not found"));
    }
}
