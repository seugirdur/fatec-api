package com.fatec.apiestacionamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity

public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID uuid;
    private String username;
    private String password;
}