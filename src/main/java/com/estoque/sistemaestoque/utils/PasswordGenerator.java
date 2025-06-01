package com.estoque.sistemaestoque.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = "admin123";
        String hash = encoder.encode(senha);
        System.out.println("Hash para a senha '" + senha + "': " + hash);
    }
}