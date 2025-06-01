package com.estoque.sistemaestoque.services;

import java.util.List;

import com.estoque.sistemaestoque.dtos.ProdutoDTO;
import com.estoque.sistemaestoque.dtos.ProdutoEstoqueDTO;
import com.estoque.sistemaestoque.dtos.ProdutoLucroDTO;

public interface ProdutoService {
    ProdutoDTO save(ProdutoDTO dto);

    ProdutoDTO update(Long id, ProdutoDTO dto);

    void delete(Long id);

    ProdutoDTO findById(Long id);

    List<ProdutoDTO> findAll();

    ProdutoDTO findByCodigo(String codigo);

    List<ProdutoDTO> buscarPorTipo(Integer tipoId);

    List<ProdutoDTO> buscarProdutosComEstoqueBaixo(Integer quantidadeMinima);

    List<ProdutoEstoqueDTO> consultarEstoquePorTipo(Long tipoId);

    List<ProdutoLucroDTO> consultarLucroPorProduto();
}