package com.app.quizz.repository;

import com.app.quizz.model.Materi;
import com.app.quizz.model.Score;
import com.app.quizz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {
    Optional<Score> findAllByMateriIdAndUserId(Materi materiId, User userId);
    Optional<Score> findByIdAndUserId(String scoreId, User userId);
    List<Score> findAllByUserId(User userId);
}
