package com.capstoneproject.employeecertificationbackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Component()
@Table(name = "result")
@Entity(name = "result")
public class Result {

    @Id
    @SequenceGenerator(
            name = "result_sequence",
            sequenceName = "result_sequence"
    )
    @GeneratedValue(
            generator = "result_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    private String question;

    private String correct;
    
    private String answerOfUser;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "test_id",
            referencedColumnName = "test_id",
            nullable = false)
    private Test test;
    
    

    public Result() {
    }

    public Result(String question, String correct, String answerOfUser) {
        this.question = question;
        this.correct = correct;
        this.answerOfUser = answerOfUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAnswerOfUser() {
        return answerOfUser;
    }

    public void setAnswerOfUser(String answerOfUser) {
        this.answerOfUser = answerOfUser;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
