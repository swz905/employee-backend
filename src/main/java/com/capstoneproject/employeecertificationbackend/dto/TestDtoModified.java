package com.capstoneproject.employeecertificationbackend.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Component
public class TestDtoModified {

    private String title;
    private String difficulty;
    private Integer score;



    public TestDtoModified() {
    }

    public TestDtoModified(String title, String difficulty, Integer score) {
        this.title = title;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
