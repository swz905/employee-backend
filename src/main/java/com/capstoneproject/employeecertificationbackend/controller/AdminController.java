package com.capstoneproject.employeecertificationbackend.controller;


import com.capstoneproject.employeecertificationbackend.dto.EmployeeDto;
import com.capstoneproject.employeecertificationbackend.exception.UserNotFoundException;
import com.capstoneproject.employeecertificationbackend.models.Admin;
import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import com.capstoneproject.employeecertificationbackend.service.AdminService;
import com.capstoneproject.employeecertificationbackend.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final ManagerService managerService;
    private final AdminService adminService;

    @Autowired
    public AdminController(ManagerService managerService, AdminService adminService) {
        this.managerService = managerService;
        this.adminService = adminService;
    }


    @GetMapping("{name}")
    public List<Manager> getAllManagers(@PathVariable String name) throws UserNotFoundException {

        return adminService.retrieveAllManagers(name);
    }

    @PutMapping("{name}/manager/{managerId}")
    public Manager updateEmployee(@PathVariable("name") String name ,
                                   @PathVariable("managerId") Long id,
                                   @Valid @RequestBody EmployeeDto employee) throws UserNotFoundException {

        Admin adminByName = adminService.findAdminByName(name);
        return managerService.updateManager(id,employee);
    }
}
