package com.capstoneproject.employeecertificationbackend.service;


import com.capstoneproject.employeecertificationbackend.models.Question;
import com.capstoneproject.employeecertificationbackend.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question addNewQuestion(Question question) {
        Optional<Question> questionByTitle =
                questionRepository.findQuestionByTitle(question.getTitle());
        if (questionByTitle.isPresent()) {
            return null;
        }
        return questionRepository.save(question);
    }

    public boolean deleteQuestion(String title) {
        Optional<Question> questionByTitle = questionRepository.findQuestionByTitle(title);
        questionRepository.delete(questionByTitle.get());
        return true;
    }

    public List<Question> getShuffledQuestions() {
        List<Question> all = questionRepository.findAll();
        Collections.shuffle(all);
        return all.stream().limit(15).collect(Collectors.toList());

    }


    public List<Question> getQuestionsByDifficulty(String difficulty) {

        List<Question> questionsByDifficulty = questionRepository.findQuestionsByDifficulty(difficulty);
        Collections.shuffle(questionsByDifficulty);
        return questionsByDifficulty.stream().limit(15).collect(Collectors.toList());

    }
}
