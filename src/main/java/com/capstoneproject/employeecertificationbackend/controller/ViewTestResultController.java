package com.capstoneproject.employeecertificationbackend.controller;


import com.capstoneproject.employeecertificationbackend.models.Result;
import com.capstoneproject.employeecertificationbackend.models.Test;
import com.capstoneproject.employeecertificationbackend.service.ViewTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/view-test-page")
public class ViewTestResultController {

    private final ViewTestResultService viewTestResultService;


    @Autowired
    public ViewTestResultController(ViewTestResultService viewTestResultService) {
        this.viewTestResultService = viewTestResultService;
    }


    @GetMapping("employee/{email}")
    public ResponseEntity<List<Test>> getAllTestOfEmployee(@PathVariable("email") String email){

        List<Test> allTestsByEmail = viewTestResultService.getAllTestsByEmail(email);
        return allTestsByEmail == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND):
                new ResponseEntity<>(allTestsByEmail,HttpStatus.OK);

    }

    @PostMapping("employee/{email}/{title}")
    public ResponseEntity<Void> addResultToTest(@PathVariable("email") String email,
                                                @PathVariable("title") String title,
                                                @RequestBody Result result){
        return viewTestResultService.addTestResult(email,title,result).isPresent()?
                new ResponseEntity<>(HttpStatus.CREATED):
                new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
    }



}
