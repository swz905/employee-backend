package com.capstoneproject.employeecertificationbackend.repo;

import com.capstoneproject.employeecertificationbackend.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

//    public List<Result> findAllByTotalCorrectOrderByTotalCorrectDesc();

    public Optional<Result> findResultByQuestion(String question);
}
