package com.fatec.apiestacionamento.DTO;

import com.fatec.apiestacionamento.annotation.EmailFatec;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    @EmailFatec
    private String email;
}
