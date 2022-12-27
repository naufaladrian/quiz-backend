package com.app.quizz.repository;


import com.app.quizz.model.User;
import com.app.quizz.model.UserTraffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTrafficRepository extends JpaRepository<UserTraffic, Integer> {
    Optional<UserTraffic> findByUserId(User userId);
}
