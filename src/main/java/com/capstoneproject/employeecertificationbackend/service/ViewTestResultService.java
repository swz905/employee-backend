package com.capstoneproject.employeecertificationbackend.service;


import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Result;
import com.capstoneproject.employeecertificationbackend.models.Test;
import com.capstoneproject.employeecertificationbackend.repo.EmployeeRepository;
import com.capstoneproject.employeecertificationbackend.repo.ResultRepository;
import com.capstoneproject.employeecertificationbackend.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ViewTestResultService {

    private final TestService testService;
    private final EmployeeRepository employeeRepository;
    private final ResultRepository resultRepository;
    private final TestRepository testRepository;

    @Autowired
    public ViewTestResultService(TestService testService, EmployeeRepository employeeRepository, ResultRepository resultRepository, TestRepository testRepository) {
        this.testService = testService;
        this.employeeRepository = employeeRepository;
        this.resultRepository = resultRepository;
        this.testRepository = testRepository;
    }


    public List<Test> getAllTestsByEmail(String email){
        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(email);
        return employeeByEmail.get().getTests();
    }

    @Transactional
    public Optional<Result> addTestResult(String email , String title, Result result){

        Test testByTitle = testRepository.findTestByTitle(title);
        Result newResult = new Result();
        newResult.setQuestion(result.getQuestion());
        newResult.setCorrect(result.getCorrect());
        newResult.setAnswerOfUser(result.getAnswerOfUser());
        newResult.setTest(testByTitle);
        testByTitle.addResult(newResult);

        return resultRepository.findResultByQuestion(result.getQuestion());


    }
}
