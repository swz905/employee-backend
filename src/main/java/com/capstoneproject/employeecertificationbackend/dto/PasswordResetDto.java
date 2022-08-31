package com.capstoneproject.employeecertificationbackend.dto;


import org.springframework.beans.factory.annotation.Required;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class PasswordResetDto {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String ResetURI;



    public PasswordResetDto() {
    }

    public PasswordResetDto(String email, String resetURI) {
        this.email = email;
        ResetURI = resetURI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getResetURI() {
        return ResetURI;
    }

    public void setResetURI(String resetURI) {
        ResetURI = resetURI;
    }
}
