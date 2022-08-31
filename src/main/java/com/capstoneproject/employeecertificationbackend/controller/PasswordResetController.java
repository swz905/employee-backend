package com.capstoneproject.employeecertificationbackend.controller;


import com.capstoneproject.employeecertificationbackend.dto.PasswordResetDto;
import com.capstoneproject.employeecertificationbackend.dto.ResetPasswordDto;
import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class PasswordResetController {

    private final EmployeeService employeeService;


    @Autowired
    public PasswordResetController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("changePassword")
    public ResponseEntity<Void> setPasswordResetToken(@RequestBody PasswordResetDto passwordResetDto){

        Optional<Employee> employee = employeeService.sendPasswordResetLinkToEmployee(passwordResetDto);
        if(employee.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("resetPassword")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){

        Optional<Employee> employee = employeeService.resetEmployeePassword(resetPasswordDto);
        if(employee.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getByToken")
    public Optional<Employee> getEmployeeByEmailAndToken(@RequestBody ResetPasswordDto resetPasswordDto){
        return employeeService.getEmployeeByEmailAndToken(resetPasswordDto);
    }
}
