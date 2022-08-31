package com.capstoneproject.employeecertificationbackend.repo;

import com.capstoneproject.employeecertificationbackend.models.Employee;
import com.capstoneproject.employeecertificationbackend.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Long> {

//    @Query("FROM Employee e where e.manager_id = :userId")
    List<Employee> findAllByManager(Optional<Manager> manager);

    public Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByToken(String resetToken);

    Optional<Employee> findEmployeeByEmailAndToken(String email, String token);


}
