package com.capstoneproject.employeecertificationbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "manager")
@Table(name = "manager")
public class Manager implements Serializable {

    @Id
    @SequenceGenerator(
            name="manager_sequence",
            sequenceName = "manager_sequence",
            initialValue = 2000,
            allocationSize = 10
    )
    @GeneratedValue(
            generator ="manager_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "manager_id",
            nullable = false,
            updatable = false
    )
    private long manager_id;

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
    @Email
    private String email;

    @JsonIgnore
    @Column(
            name="password",
            nullable = false
    )
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

    @OneToMany(mappedBy = "manager",
            cascade=CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Employee> reportees = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            name = "admin_id",
            referencedColumnName = "admin_id",
            nullable = false)
    private Admin admin;

    public Manager() {
    }

    public Manager(String name, String email, String password, String phoneNumber, String empType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.empType = empType;
    }

    public long getManager_id() {
        return manager_id;
    }

    public void setManager_id(long manager_id) {
        this.manager_id = manager_id;
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

    @Override
    public String toString() {
        return "Manager{" +
                "manager_id=" + manager_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", empType='" + empType + '\'' +
                '}';
    }

//    @JsonBackReference
    public List<Employee> getReportees() {
        return reportees;
    }

    public void addReportee(Employee employee) {
        if (!this.reportees.contains(employee)){
            this.reportees.add(employee);
        }
    }
    public void removeReportee(Employee employee){
        if (this.reportees.contains(employee)){
            this.reportees.remove(employee);
        }
    }

    @JsonBackReference
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
