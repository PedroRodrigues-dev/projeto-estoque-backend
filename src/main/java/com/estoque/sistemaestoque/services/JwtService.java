package com.estoque.sistemaestoque.services;

import com.estoque.sistemaestoque.entities.Usuario;

public interface JwtService {
    String generateToken(Usuario user);

    String extractUsername(String token);

    boolean isTokenValid(String token, Usuario user);
}
