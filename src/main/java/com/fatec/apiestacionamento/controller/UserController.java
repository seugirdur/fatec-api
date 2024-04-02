package com.fatec.apiestacionamento.controller;

import com.fatec.apiestacionamento.DTO.LoginRequestDTO;
import com.fatec.apiestacionamento.DTO.RegisterRequestDTO;
import com.fatec.apiestacionamento.model.User;
import com.fatec.apiestacionamento.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController("/")
public class UserController {


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDTO request) {
        // Implementação da lógica de login
        return "Login realizado com sucesso!";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {

        if (userService.doesUserExistByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists!");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Save the user to the database
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setVerified(false); // Assuming this is the field for verification status
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro realizado com sucesso!");
    }




    @GetMapping("/auth/{email}")
    public ResponseEntity<String> requestAuthenticationCode(@PathVariable String email) {
        // Generate and send a new authentication code
        userService.sendVerificationCode(email);
        return ResponseEntity.status(HttpStatus.OK).body("New authentication code sent to " + email);
    }
}