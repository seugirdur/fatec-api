package com.fatec.apiestacionamento.controller;

import com.fatec.apiestacionamento.DTO.LoginRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {
        // Implementação da lógica de login
        return "Login realizado com sucesso!";
    }
}