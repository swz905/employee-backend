package com.capstoneproject.employeecertificationbackend.controller;


import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import com.capstoneproject.employeecertificationbackend.service.EmployeeService;
import com.capstoneproject.employeecertificationbackend.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api")
public class ManagerController {

    private final EmployeeService employeeService;
    private final ManagerService managerService;

    @Autowired
    public ManagerController(EmployeeService employeeService, ManagerService managerService) {
        this.employeeService = employeeService;
        this.managerService = managerService;
    }

    @GetMapping("manager/{name}/employees")
    public ResponseEntity<List<Employee>> getAllEmployeesUnderManager(@PathVariable("name") String name){

        List<Employee> employees = employeeService.retrieveAllUsersUnderManager(name);
        if(employees.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("manager/{email}/employees-with-results")
    public ResponseEntity<List<Employee>> getAllEmployeesUnderManagerByEmail(@PathVariable("email") String email){

        List<Employee> employees = employeeService.retrieveAllUsersUnderManagerByEmail(email);
        if(employees.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("getmanagers")
    public List<Manager> getManagers(){
        return managerService.getAllManagers();
    }

}
