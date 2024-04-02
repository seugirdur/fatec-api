package com.fatec.apiestacionamento.repository;

import com.fatec.apiestacionamento.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsernameAndPassword(String username, String password);

    boolean existsByEmail(String email);
}
