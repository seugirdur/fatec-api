package com.fatec.apiestacionamento.DTO;

import com.fatec.apiestacionamento.annotation.EmailFatec;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank
    @EmailFatec
    @Email
    private String email;

    @NotBlank
    private String password;
}
