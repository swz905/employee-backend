package com.capstoneproject.employeecertificationbackend.repo;

import com.capstoneproject.employeecertificationbackend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {


    public Optional<Question> findQuestionByTitle(String string);

    @Modifying
    @Transactional
    public int deleteQuestionByTitle(String title);

    public List<Question> findQuestionsByDifficulty(String difficulty);
}
