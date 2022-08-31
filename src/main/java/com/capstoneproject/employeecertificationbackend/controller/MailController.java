package com.capstoneproject.employeecertificationbackend.controller;


import com.capstoneproject.employeecertificationbackend.service.EmployeeMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/mail")
public class MailController {

    private final EmployeeMailSender employeeMailSender;

    @Autowired
    public MailController(EmployeeMailSender employeeMailSender) {
        this.employeeMailSender = employeeMailSender;
    }

    @GetMapping("")
    public ResponseEntity<Void> giveSendRequest(){
        try {
//            employeeMailSender.sendEmail();
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (MailException e){
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);


    }
}
