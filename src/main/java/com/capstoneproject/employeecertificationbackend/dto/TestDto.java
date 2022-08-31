package com.capstoneproject.employeecertificationbackend.dto;


import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Component
public class TestDto {

    private String title;
    private String difficulty;

    @Email
    @Size(min = 3)
    private String email;

    public TestDto() {
    }

    public TestDto(String title, String difficulty, String email) {
        this.title = title;
        this.difficulty = difficulty;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
