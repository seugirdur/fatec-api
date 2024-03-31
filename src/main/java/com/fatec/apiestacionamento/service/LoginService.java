package com.fatec.apiestacionamento.service;

import com.fatec.apiestacionamento.DTO.LoginRequestDTO;
import com.fatec.apiestacionamento.model.User;
import com.fatec.apiestacionamento.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean authenticate(LoginRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // Here you would implement the logic to authenticate the user
        // For example, you might fetch the user from the repository by username and password
        // and check if the user exists and if the password matches

        // For demonstration, let's assume you have a method in the repository to find a user by username and password
        User user = loginRepository.findByUsernameAndPassword(username, password);

        // If user is not null, authentication is successful
        return user != null;
    }
}