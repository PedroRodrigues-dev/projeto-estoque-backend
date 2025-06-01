package com.estoque.sistemaestoque.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.sistemaestoque.entities.AuthRequest;
import com.estoque.sistemaestoque.entities.AuthResponse;
import com.estoque.sistemaestoque.entities.RegisterRequest;
import com.estoque.sistemaestoque.entities.Usuario;
import com.estoque.sistemaestoque.repositories.UsuarioRepository;
import com.estoque.sistemaestoque.services.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UsuarioRepository usuarioRepository;
        private final PasswordEncoder passwordEncoder;

        @PostMapping("/login")
        @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida")
        })
        public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

                Usuario user = usuarioRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

                String token = jwtService.generateToken(user);
                return ResponseEntity.ok(AuthResponse.builder().token(token).build());
        }

        @PostMapping("/register")
        @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário e retorna um token JWT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já existe")
        })
        public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
                if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
                        throw new RuntimeException("Email já cadastrado");
                }

                Usuario user = Usuario.builder()
                                .nome(request.getNome())
                                .email(request.getEmail())
                                .senhaHash(passwordEncoder.encode(request.getSenha()))
                                .papel("COMUM")
                                .build();

                usuarioRepository.save(user);
                String token = jwtService.generateToken(user);
                return ResponseEntity.ok(AuthResponse.builder().token(token).build());
        }
}
