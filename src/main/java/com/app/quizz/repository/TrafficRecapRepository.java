package com.app.quizz.repository;

import com.app.quizz.model.TrafficRecap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRecapRepository extends JpaRepository<TrafficRecap, Integer> {
}
