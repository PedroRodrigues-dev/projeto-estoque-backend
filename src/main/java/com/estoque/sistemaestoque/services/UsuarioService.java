package com.estoque.sistemaestoque.services;

import com.estoque.sistemaestoque.dtos.UsuarioDTO;
import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> findAll();

    UsuarioDTO findById(Long id);

    UsuarioDTO save(UsuarioDTO dto);

    UsuarioDTO update(Long id, UsuarioDTO dto);

    void delete(Long id);
}