package com.app.quizz.repository;

import com.app.quizz.model.Materi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriRepository extends JpaRepository<Materi, String> {
}
