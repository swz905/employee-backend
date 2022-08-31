package com.capstoneproject.employeecertificationbackend.service;


import com.capstoneproject.employeecertificationbackend.dto.TestDto;
import com.capstoneproject.employeecertificationbackend.dto.TestDtoModified;
import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Test;
import com.capstoneproject.employeecertificationbackend.repo.EmployeeRepository;
import com.capstoneproject.employeecertificationbackend.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final EmployeeMailSender employeeMailSender;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TestService(TestRepository testRepository, EmployeeMailSender employeeMailSender, EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.testRepository = testRepository;
        this.employeeMailSender = employeeMailSender;
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    public List<Test> getAllTest() {
        return testRepository.findAll();
    }

    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }


    @Transactional
    public void createNewTest(TestDto testDto) {

        Optional<Employee> employee = employeeService.getEmployeeByEmail(testDto.getEmail());
        Test test = new Test();
        test.setDifficulty(testDto.getDifficulty());
        test.setTitle(testDto.getTitle());
        test.setEmployee(employee.get());
        employee.get().addTest(test);

        String url = String.format("localhost://4200/welcome/:%s", employee.get().getName());
        try {
            employeeMailSender.sendEmailToResetPassword(testDto.getEmail(), url);
        } catch (MailException | MalformedURLException e) {
            e.getMessage();
        }
    }

    public List<Test> getAllTestsByEmail(String email) {
        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(email);
        return testRepository.findAllTestByEmployee(employeeByEmail.get());
    }

    @Transactional
    public void addScoreToTest(String email, TestDtoModified test) {

        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(email);
//        testRepository.findAllTestByEmployee(employeeByEmail.get())
//                .stream()
//                .filter(test1 -> test1.getTitle().equals(test.getTitle()))
//                .findFirst()
//                .get()
//                .setScore(test.getScore());
        Test testByTitle = testRepository.findTestByTitle(test.getTitle());
        testByTitle.setScore(test.getScore());
        testByTitle.setAttempted(true);

    }
}
