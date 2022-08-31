package com.capstoneproject.employeecertificationbackend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "employee")
@Table(name = "employee")
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            initialValue = 1001,
            allocationSize = 10
    )
    @GeneratedValue(
            generator = "employee_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "employee_id",
            nullable = false,
            updatable = false
    )
    private Long employee_id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    @JsonIgnore
    private String password;
    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phoneNumber;
    @Column(
            name = "emp_type",
            nullable = false
    )
    private String empType;

    @Column(name = "first_login")
    private boolean firstLogin;

    @Column(name="token")
    private String token;

    @ManyToOne
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "manager_id",
            nullable = false)
    private Manager manager;

    @OneToMany(mappedBy = "employee",
            cascade=CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Test> tests;

    public Employee() {
    }

    public Employee(String name, String email, String password, String phoneNumber, String empType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.empType = empType;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @JsonBackReference
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }


    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
    public  void addTest(Test test){
        this.tests.add(test);
    }
    public void removeTest(Test test){
        if (this.tests.contains(test)) {
            this.tests.remove(test);
        }
    }





    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", empType='" + empType + '\'' +
                '}';
    }
}
