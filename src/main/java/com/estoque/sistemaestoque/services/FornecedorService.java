package com.estoque.sistemaestoque.services;

import java.util.List;

import com.estoque.sistemaestoque.dtos.FornecedorDTO;

public interface FornecedorService {

    List<FornecedorDTO> findAll();

    FornecedorDTO findById(Long id);

    FornecedorDTO save(FornecedorDTO dto);

    FornecedorDTO update(Long id, FornecedorDTO dto);

    void delete(Long id);
}