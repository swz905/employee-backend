package com.capstoneproject.employeecertificationbackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Test {

    @Id
    @SequenceGenerator(
            name = "test_sequence",
            sequenceName = "test_sequence",
            initialValue = 7000,
            allocationSize = 10
    )
    @GeneratedValue(
            generator = "test_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "test_id",
            nullable = false,
            updatable = false
    )
    private Long testId;

    @Column(
            name = "title",
            nullable = false,
            updatable = false
    )
    @NotNull
    private String title;

    @Column(
            name = "difficulty",
            nullable = false,
            updatable = false
    )
    private String difficulty;

    @Column(
            name = "score"
    )
    private Integer score;

    @Column(
            name = "attempted"
    )
    private boolean attempted;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "test",
            cascade=CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Result> results;

    public Test(String title, String difficulty, Integer score) {
        this.title = title;
        this.difficulty = difficulty;
        this.score = score;
    }

    public Test() {
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
    public void addResult(Result result){
        this.results.add(result);
    }
}
