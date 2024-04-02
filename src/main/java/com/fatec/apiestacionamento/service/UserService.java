package com.fatec.apiestacionamento.service;

import com.fatec.apiestacionamento.DTO.LoginRequestDTO;
import com.fatec.apiestacionamento.model.User;
import com.fatec.apiestacionamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AzureMailService azureMailService;

    @Autowired
    public UserService(UserRepository userRepository, AzureMailService azureMailService) {
        this.userRepository = userRepository;
        this.azureMailService = azureMailService;
    }

    public void sendVerificationCode(String email) {
        // Generate a random 6-digit verification code
        String verificationCode = generateVerificationCode();

        // Send verification code via email
        azureMailService.sendVerificationEmail(email, verificationCode);
    }

    public boolean doesUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(User user) {
        userRepository.save(user);
        sendVerificationCode(user.getEmail());
    }

    private String generateVerificationCode() {
        // Generate and return a random 6-digit code (implement your logic here)
        return "123456"; // Example code, replace with actual logic
    }
}