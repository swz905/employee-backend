package com.capstoneproject.employeecertificationbackend.service;


import com.capstoneproject.employeecertificationbackend.exception.UserNotFoundException;
import com.capstoneproject.employeecertificationbackend.models.Admin;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import com.capstoneproject.employeecertificationbackend.repo.AdminRepository;
import com.capstoneproject.employeecertificationbackend.repo.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {


    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, ManagerRepository managerRepository) {
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
    }


    public List<Manager> retrieveAllManagers(String name) throws UserNotFoundException {

        Admin admin = adminRepository.findAdminByName(name).orElseThrow(UserNotFoundException::new);

        return managerRepository.findAllByAdmin(admin);
    }

    public Admin findAdminByName(String name) throws UserNotFoundException {
        Admin admin = adminRepository.findAdminByName(name).orElseThrow(UserNotFoundException::new);
        return admin;
    }
}
