package com.example.demo.Repository;

import com.example.demo.Model.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Requests, Integer> {
    Requests findRequestByRequestId(Integer requestId);
}
