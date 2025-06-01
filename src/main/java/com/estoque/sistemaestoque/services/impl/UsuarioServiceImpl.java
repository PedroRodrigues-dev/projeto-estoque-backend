package com.estoque.sistemaestoque.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estoque.sistemaestoque.dtos.UsuarioDTO;
import com.estoque.sistemaestoque.entities.Usuario;
import com.estoque.sistemaestoque.mappers.UsuarioMapper;
import com.estoque.sistemaestoque.repositories.UsuarioRepository;
import com.estoque.sistemaestoque.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        usuario = repository.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setPapel(dto.getPapel());

        usuario = repository.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}