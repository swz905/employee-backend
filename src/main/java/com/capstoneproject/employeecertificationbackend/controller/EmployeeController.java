package com.capstoneproject.employeecertificationbackend.controller;

import com.capstoneproject.employeecertificationbackend.dto.EmployeeDto;
import com.capstoneproject.employeecertificationbackend.enums.EmpType;
import com.capstoneproject.employeecertificationbackend.exception.UserNotFoundException;
import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.service.EmployeeService;
import com.capstoneproject.employeecertificationbackend.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
//@CrossOrigin(origins = "http://localhost:4200")


@RestController
@RequestMapping("api")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ManagerService managerService;
    private final EmployeeDto employeeDto;


    @Autowired
    public EmployeeController(EmployeeService employeeService, ManagerService managerService, EmployeeDto employeeDto) {
        this.employeeService = employeeService;
        this.managerService = managerService;
        this.employeeDto = employeeDto;
    }


//    @RequestMapping(
//            value = "users/addUser",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void createUser(@RequestBody Employee employee) throws UserNotFoundException {
//
//        if (employee.getEmpType().equals("manager")) {
//            managerService.createNewManagerFromEmployee(employee);
//        } else {
//            employeeService.createNewUser(employee);
//        }
//    }

    @GetMapping("employees/getAll")
    public List<Employee> getAllEmployees(){

        return employeeService.retrieveAllUsers();

    }

    @GetMapping("employees/{email}/{password}")
    public ResponseEntity<EmployeeDto> getEmployeeByEmail(@PathVariable("email") String email,
                                                         @PathVariable("password") String password){
        EmployeeDto employeeByEmailAndPassword = employeeService.getEmployeeByEmailAndPassword(email, password);
        if(employeeByEmailAndPassword!=null) {
            return new ResponseEntity<>(employeeByEmailAndPassword, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("employees/{employeeId}")
    public Employee updateEmployee(@PathVariable("employeeId") Long id ,
                                  @Valid @RequestBody EmployeeDto employee) throws UserNotFoundException {

        return employeeService.updateEmployee(id, employee);
    }


    @PostMapping("employees/addEmployee")
    public ResponseEntity<Void> addNewEmployee(@Valid @RequestBody EmployeeDto employee) throws UserNotFoundException {
        if (employee.getEmpType().equals("employee")){
            return (employeeService.createNewUser(employee) != null) ?
                    new ResponseEntity<>(HttpStatus.CREATED) :
                    new ResponseEntity<>(HttpStatus.CONFLICT);

        }
        else if (employee.getEmpType().equals(EmpType.manager.toString())){
            if(managerService.createNewUser(employee) != null) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
//        else if (employee.getEmpType().equals(EmpType.admin.toString())){
//            admin.createNewUser(employee);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
        return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
