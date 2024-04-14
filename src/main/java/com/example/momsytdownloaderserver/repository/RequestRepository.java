package com.example.momsytdownloaderserver.repository;

import com.example.momsytdownloaderserver.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
}
