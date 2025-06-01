package com.estoque.sistemaestoque.services;

import java.time.LocalDateTime;
import java.util.List;

import com.estoque.sistemaestoque.dtos.MovimentoEstoqueDTO;

public interface MovimentoEstoqueService {
    MovimentoEstoqueDTO save(MovimentoEstoqueDTO dto);

    MovimentoEstoqueDTO findById(Long id);

    List<MovimentoEstoqueDTO> findAll();

    List<MovimentoEstoqueDTO> findByProduto(Long produtoId);

    List<MovimentoEstoqueDTO> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);
}