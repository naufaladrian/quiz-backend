package com.app.quizz.repository;

import com.app.quizz.model.Materi;
import com.app.quizz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findAllByMateriId(Materi materiId);

}
