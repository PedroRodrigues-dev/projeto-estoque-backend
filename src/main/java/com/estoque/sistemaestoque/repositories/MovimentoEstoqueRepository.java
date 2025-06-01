package com.estoque.sistemaestoque.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estoque.sistemaestoque.entities.MovimentoEstoque;
import com.estoque.sistemaestoque.entities.Produto;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {
    List<MovimentoEstoque> findByProdutoId(Long produtoId);

    List<MovimentoEstoque> findByProdutoIdAndTipoMovimentacao(Long produtoId, String tipoMovimentacao);

    List<MovimentoEstoque> findByProdutoAndTipoMovimentacao(Produto produto, String tipoMovimentacao);

    List<MovimentoEstoque> findByDataMovimentacaoBetween(LocalDateTime inicio, LocalDateTime fim);
}