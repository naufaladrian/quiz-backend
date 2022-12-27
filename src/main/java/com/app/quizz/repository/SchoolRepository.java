package com.app.quizz.repository;

import com.app.quizz.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
    @Query(value = "select * from school order by rand()", nativeQuery = true)
    List<School> getRandomAllSchool();

    List<School> findAllByNameIgnoreCaseContainingOrderByIdAsc(String name);

}
