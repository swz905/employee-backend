package com.capstoneproject.employeecertificationbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.passay.spring.*;

public class ResetPasswordDto {

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String token;

    public ResetPasswordDto() {
    }

    public ResetPasswordDto(String password, String confirmPassword, String email, String token) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
