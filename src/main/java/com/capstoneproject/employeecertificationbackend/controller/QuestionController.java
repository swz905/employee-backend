package com.capstoneproject.employeecertificationbackend.controller;


import com.capstoneproject.employeecertificationbackend.models.Question;
import com.capstoneproject.employeecertificationbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("getShuffled")
    @ResponseStatus(HttpStatus.OK)
    public List<Question> getShuffled(){
        return questionService.getShuffledQuestions();
    }


    @GetMapping("{difficulty}")
    public ResponseEntity<List<Question>> getQuestionsByDifficulty(@PathVariable("difficulty") String difficulty){
        List<Question> questionsByDifficulty = questionService.getQuestionsByDifficulty(difficulty);
        return questionsByDifficulty.isEmpty()?
                new ResponseEntity<>(HttpStatus.NOT_FOUND):
                new ResponseEntity<>(questionsByDifficulty, HttpStatus.OK);
    }



    @PostMapping("add")
    public ResponseEntity<Question> getAllQuestions(@RequestBody Question question){

        Question question1 = questionService.addNewQuestion(question);
        return question1 ==(null)?
                new ResponseEntity<>(HttpStatus.CONFLICT):
                new ResponseEntity<>(question1, HttpStatus.CREATED);
    }
    
    @DeleteMapping("delete/{title}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String title){

        return questionService.deleteQuestion(title)?
                new ResponseEntity<>(HttpStatus.ACCEPTED):
                new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}