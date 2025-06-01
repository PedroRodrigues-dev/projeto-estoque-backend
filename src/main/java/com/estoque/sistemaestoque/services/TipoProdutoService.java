package com.estoque.sistemaestoque.services;

import java.util.List;

import com.estoque.sistemaestoque.dtos.TipoProdutoDTO;

public interface TipoProdutoService {

    List<TipoProdutoDTO> findAll();

    TipoProdutoDTO findById(Long id);

    TipoProdutoDTO save(TipoProdutoDTO dto);

    TipoProdutoDTO update(Long id, TipoProdutoDTO dto);

    void delete(Long id);
}