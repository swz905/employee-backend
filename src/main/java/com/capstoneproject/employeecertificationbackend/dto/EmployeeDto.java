package com.capstoneproject.employeecertificationbackend.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Component
public class EmployeeDto {

    private Long id ;
    private String name;
    @Email
    @Size(min = 3)
    private String email;
    private String password;
    private String phoneNumber;
    private String empType;
    private boolean firstLogin;
    private String token;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, String email, String password, String empType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.empType = empType;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
