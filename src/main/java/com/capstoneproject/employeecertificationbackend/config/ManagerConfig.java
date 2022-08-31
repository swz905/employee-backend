package com.capstoneproject.employeecertificationbackend.config;

import com.capstoneproject.employeecertificationbackend.models.Admin;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import com.capstoneproject.employeecertificationbackend.repo.AdminRepository;
import com.capstoneproject.employeecertificationbackend.repo.ManagerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ManagerConfig {

    @Bean
    CommandLineRunner commandLineRunner(ManagerRepository managerRepository , AdminRepository adminRepository) {
        return args -> {

            Admin admin1 = new Admin(
                    "adminAdp",
                    "Admin@ADP.com",
                    "admin",
                    "admin"
            );


            Manager manager1 = new Manager(
                    "balaji",
                    "balaji@adp.com",
                    "pass123",
                    "1234566",
                    "manager");

            manager1.setAdmin(admin1);
            admin1.getManagers().add(manager1);


            Manager manager2 = new Manager(
                    "ameen",
                    "ameen@adp.com",
                    "123456",
                    "98772432",
                    "manager");

            manager2.setAdmin(admin1);
            admin1.getManagers().add(manager2);
            adminRepository.save(admin1);

        };
    }
}

