package com.example.momsytdownloaderserver.repository;

import com.example.momsytdownloaderserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User saveUser(User user);

    Optional<User> findByUsernameAndName(String username, String name);
}
