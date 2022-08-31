package com.capstoneproject.employeecertificationbackend.service;


import com.capstoneproject.employeecertificationbackend.dto.EmployeeDto;
import com.capstoneproject.employeecertificationbackend.dto.PasswordResetDto;
import com.capstoneproject.employeecertificationbackend.dto.ResetPasswordDto;
import com.capstoneproject.employeecertificationbackend.exception.UserNotFoundException;
import com.capstoneproject.employeecertificationbackend.models.Admin;
import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import com.capstoneproject.employeecertificationbackend.repo.AdminRepository;
import com.capstoneproject.employeecertificationbackend.repo.EmployeeRepository;
import com.capstoneproject.employeecertificationbackend.repo.ManagerRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMailSender employeeMailSender;
    private final EmployeePasswordGeneratorService employeePasswordGeneratorService;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMailSender employeeMailSender, EmployeePasswordGeneratorService employeePasswordGeneratorService, ManagerRepository managerRepository, AdminRepository adminRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMailSender = employeeMailSender;
        this.employeePasswordGeneratorService = employeePasswordGeneratorService;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
    }

    @PostConstruct
    @Transactional
    public void initDB() throws UserNotFoundException {
        Faker faker = new Faker();


        Admin admin = new Admin("admin",
                "admin123@adp.com",
                faker.internet().password(),
                "admin");


        Manager manager1 = new Manager(
                "sudappi",
                "sudappi@adp.com",
                "pass123",
                "1234566",
                "manager");
        manager1.setAdmin(admin);
        admin.getManagers().add(manager1);

        //student creation using faker
        String name = faker.name().fullName();
        String email = String.format("%s@ADP.com", name);
        String password = faker.internet().password();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String empType = "employee";
        Employee employee1 = new Employee(name,
                email,
                password,
                phoneNumber,
                empType);

        manager1.addReportee(employee1);
        employee1.setManager(manager1);

        Employee employee2 = new Employee(
                "sugunan",
                "sugunan@adp.com",
                faker.internet().password(),
                faker.phoneNumber().phoneNumber(),
                "employee");
        manager1.addReportee(employee2);
        employee2.setManager(manager1);

//        managerRepository.save(manager1);
        adminRepository.save(admin);


    }





    @Transactional
    public Employee createNewUser(EmployeeDto employee) throws UserNotFoundException {

        Employee newEmployee = new Employee();
        newEmployee.setName(employee.getName());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setEmpType(employee.getEmpType());
        newEmployee.setPhoneNumber(employee.getPhoneNumber());
        String token = UUID.randomUUID().toString();
        newEmployee.setToken(token);
//        newEmployee.setManager(employee.);

        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (employeeByEmail.isEmpty()) {
            newEmployee.setPassword(employeePasswordGeneratorService.generateCommonLangPassword());
            Manager byId = managerRepository.findById(2001L).orElseThrow(UserNotFoundException::new);

            byId.addReportee(newEmployee);
            newEmployee.setManager(byId);
            Employee save = employeeRepository.save(newEmployee);


            try {
                employeeMailSender.sendEmail(newEmployee.getEmail(), newEmployee.getPassword());
            } catch (MailException mailException) {
                mailException.getMessage();
            }
            return save;
        }
        return null;
    }


    public List<Employee> retrieveAllUsers() {
        return employeeRepository.findAll();
    }

    public List<Employee> retrieveAllUsersUnderManager(String name) {
        Optional<Manager> byId = managerRepository.findManagerByName(name);
        return employeeRepository.findAllByManager(byId);
    }

    public List<Employee> retrieveAllUsersUnderManagerByEmail(String email) {
        Optional<Manager> byId = managerRepository.findManagerByEmail(email);
        return employeeRepository.findAllByManager(byId);
    }

    public EmployeeDto getEmployeeByEmailAndPassword(String email, String password) {
        EmployeeDto employeeDto = new EmployeeDto();

        if (employeeRepository.findEmployeeByEmail(email).stream().anyMatch(user -> user.getPassword().equals(password))) {
            Employee employee = employeeRepository.findEmployeeByEmail(email).get();
            employeeDto.setId(employee.getEmployee_id());
            employeeDto.setEmail(employee.getEmail());
            employeeDto.setPassword(employee.getPassword());
            employeeDto.setName(employee.getName());
            employeeDto.setEmpType(employee.getEmpType());
            employeeDto.setEmpType(employee.getEmpType());
            employeeDto.setFirstLogin(employee.isFirstLogin());
//            String token = UUID.randomUUID().toString();
//            employeeDto.setToken(token);

            return employeeDto;
        } else if (managerRepository.findManagerByEmail(email).stream().anyMatch(user -> user.getPassword().equals(password))) {
            Manager employee = managerRepository.findManagerByEmail(email).get();
            employeeDto.setId(employee.getManager_id());
            employeeDto.setEmail(employee.getEmail());
            employeeDto.setPassword(employee.getPassword());
            employeeDto.setName(employee.getName());
            employeeDto.setEmpType(employee.getEmpType());

            return employeeDto;
        } else if (adminRepository.findAdminByEmail(email).stream().anyMatch(user -> user.getPassword().equals(password))) {
            Admin employee = adminRepository.findAdminByEmail(email).get();
            employeeDto.setId(employee.getAdmin_id());
            employeeDto.setEmail(employee.getEmail());
            employeeDto.setPassword(employee.getPassword());
            employeeDto.setName(employee.getName());
            employeeDto.setEmpType(employee.getEmpType());

            return employeeDto;
        }
        return null;
    }


    public Employee updateEmployee(Long id, EmployeeDto employee) throws UserNotFoundException {
        Employee byId = employeeRepository.findById(id).orElseThrow(UserNotFoundException::new);
        byId.setEmail(employee.getEmail());
        byId.setName(employee.getName());
        byId.setPhoneNumber(employee.getPhoneNumber());
        employeeRepository.save(byId);

        return byId;
    }

    public Optional<Employee> sendPasswordResetLinkToEmployee(PasswordResetDto passwordResetDto){

        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(passwordResetDto.getEmail());
        if(employeeByEmail.isPresent()){
            String token = UUID.randomUUID().toString();
            employeeByEmail.get().setToken(token);
            String url = passwordResetDto.getResetURI() +
                    "?token=" + token+
                    "&email="+passwordResetDto.getEmail();
            try {
                employeeMailSender.sendEmailToResetPassword(passwordResetDto.getEmail(),url);
            } catch (MailException | MalformedURLException e) {
                e.getMessage();
            }
            return employeeByEmail;

        }
        return Optional.empty();

    }

    public Optional<Employee> resetEmployeePassword(ResetPasswordDto resetPasswordDto){

        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(
                resetPasswordDto.getEmail());
        if(employeeByEmail.isPresent()){

            employeeByEmail.get().setPassword(resetPasswordDto.getPassword());
            employeeByEmail.get().setFirstLogin(true);
//            employeeRepository.save(employeeByEmail.get());
            return employeeByEmail;

        }
        return Optional.empty();

    }

    public Optional<Employee> getEmployeeByEmailAndToken(ResetPasswordDto resetPasswordDto){
        return employeeRepository.findEmployeeByEmailAndToken(resetPasswordDto.getEmail(),resetPasswordDto.getToken());
    }

    public Optional<Employee> getEmployeeByEmail(String email){
        return employeeRepository.findEmployeeByEmail(email);
    }
}

