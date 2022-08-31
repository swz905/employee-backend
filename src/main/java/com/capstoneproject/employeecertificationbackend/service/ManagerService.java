package com.capstoneproject.employeecertificationbackend.service;


import com.capstoneproject.employeecertificationbackend.dto.EmployeeDto;
import com.capstoneproject.employeecertificationbackend.exception.UserNotFoundException;
import com.capstoneproject.employeecertificationbackend.models.Admin;
import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import com.capstoneproject.employeecertificationbackend.repo.AdminRepository;
import com.capstoneproject.employeecertificationbackend.repo.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final EmployeePasswordGeneratorService employeePasswordGeneratorService;
    private final AdminRepository adminRepository;
    private final EmployeeMailSender employeeMailSender;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, EmployeePasswordGeneratorService employeePasswordGeneratorService, AdminRepository adminRepository, EmployeeMailSender employeeMailSender) {
        this.managerRepository = managerRepository;
        this.employeePasswordGeneratorService = employeePasswordGeneratorService;
        this.adminRepository = adminRepository;
        this.employeeMailSender = employeeMailSender;
    }


    public void createNewManager(Manager manager) {
        manager.setPassword(employeePasswordGeneratorService.generateCommonLangPassword());
        Manager save = managerRepository.save(manager);
    }

    @Transactional
    public void createNewManagerFromEmployee(Employee employee) throws UserNotFoundException {

        Admin admin = adminRepository.findById(5000L).orElseThrow(UserNotFoundException::new);

        String name = employee.getName();
        String email = employee.getEmail();
        String empType = employee.getEmpType();
        String phoneNumber = employee.getPhoneNumber();
        String password = employeePasswordGeneratorService.generateCommonLangPassword();
        Manager newManager = new Manager(name,email,password,phoneNumber,empType);
        newManager.setAdmin(admin);
        admin.getManagers().add(newManager);
        managerRepository.save(newManager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }


    public Manager updateManager(Long id , EmployeeDto employee) throws UserNotFoundException {
        Manager byId = managerRepository.findById(id).orElseThrow(UserNotFoundException::new);
        byId.setEmail(employee.getEmail());
        byId.setName(employee.getName());
        byId.setPhoneNumber(employee.getPhoneNumber());
        managerRepository.save(byId);

        return byId;
    }


    public Manager createNewUser(EmployeeDto employee) throws UserNotFoundException {

        Admin admin = adminRepository.findById(5000L).orElseThrow(UserNotFoundException::new);


        Manager newEmployee = new Manager();
        newEmployee.setName(employee.getName());
        newEmployee.setEmail(employee.getEmail()) ;
        newEmployee.setEmpType(employee.getEmpType());
        newEmployee.setPhoneNumber(employee.getPhoneNumber());
//        newEmployee.setManager(employee.);

        Optional<Manager> employeeByEmail = managerRepository.findManagerByEmail(employee.getEmail());
        if (employeeByEmail.isEmpty()) {
            newEmployee.setPassword(employeePasswordGeneratorService.generateCommonLangPassword());
            Manager byId = managerRepository.findById(2001L).orElseThrow(UserNotFoundException::new);

            newEmployee.setAdmin(admin);
            admin.getManagers().add(newEmployee);

            try {
                employeeMailSender.sendEmail(newEmployee.getEmail(), newEmployee.getPassword());
            } catch (MailException mailException) {
                mailException.getMessage();
            }
            return managerRepository.save(newEmployee);

        }
        return null;


    }
}
