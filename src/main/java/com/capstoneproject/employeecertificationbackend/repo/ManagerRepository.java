package com.capstoneproject.employeecertificationbackend.repo;

import com.capstoneproject.employeecertificationbackend.models.Admin;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {


    public List<Manager> findAllByAdmin(Admin admin);
    Optional<Manager> findManagerByEmail(String email);
    Optional<Manager> findManagerByName(String name);
}
