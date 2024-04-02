package com.fatec.apiestacionamento.service;

import com.fatec.apiestacionamento.DTO.LoginRequestDTO;
import com.fatec.apiestacionamento.model.User;
import com.fatec.apiestacionamento.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AzureMailService azureMailService;
    private final PasswordEncoder passwordEncoder;

    public boolean doesUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false; // User not found
        }
        return passwordEncoder.matches(password, user.getPassword());
    }

    @CachePut(value = "verificationCodes", key = "#email")
    public String sendVerificationCode(String email) {
        // Generate a random 6-digit verification code
        String verificationCode = generateVerificationCode();

        // Send verification code via email
//        azureMailService.sendVerificationEmail(email, verificationCode);

        return verificationCode;
    }

    @CachePut(value = "cacheExample", key = "'chave'")
    public String putValueInCache() {
        return "123";
    }

    @Cacheable(value = "cacheExample", key = "'chave'")
    public String getValueFromCache() {
        return null; // This will be replaced by the value retrieved from cache
    }

    @Cacheable(value = "verificationCodes", key = "#email")
    public String getVerificationCode(String email) {
        // This method will not be invoked if the verification code is already in the cache
        return null;
    }

    public boolean verifyVerificationCode(String email, String enteredCode) {
        // Retrieve verification code associated with the email from the cache
        String storedCode = getVerificationCode(email);

        // Check if verification code exists and matches the entered code
        return storedCode != null && storedCode.equals(enteredCode);
    }

    private String generateVerificationCode() {
        // Generate and return a random 6-digit code (implement your logic here)
        //generate a random number between 100000 and 999999
        int code = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
        return String.valueOf(code); // Convert to string
    }

    public void register(User user) {
        userRepository.save(user);
        sendVerificationCode(user.getEmail());
    }



    public void updateVerificationStatus(String email, boolean b) {
        // Update the verification status of the user with the specified email
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setVerified(b);
            userRepository.save(user);
        }
    }
}