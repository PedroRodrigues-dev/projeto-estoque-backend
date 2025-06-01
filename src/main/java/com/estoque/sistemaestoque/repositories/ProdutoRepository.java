package com.estoque.sistemaestoque.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.estoque.sistemaestoque.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByTipoProdutoId(Integer tipoProdutoId);

    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque <= :quantidadeMinima")
    List<Produto> findByEstoqueBaixo(Integer quantidadeMinima);

    Optional<Produto> findByCodigo(String codigo);

    @Query("""
                SELECT p FROM Produto p
                LEFT JOIN FETCH p.tipoProduto
                WHERE p.tipoProduto.id = :tipoId
            """)
    List<Produto> findByTipoProdutoWithMovimentacoes(Long tipoId);

    @Query("""
                SELECT p FROM Produto p
                LEFT JOIN FETCH p.tipoProduto
                WHERE EXISTS (
                    SELECT 1 FROM MovimentoEstoque m
                    WHERE m.produto = p
                    AND m.tipoMovimentacao = 'SAIDA'
                )
            """)
    List<Produto> findProdutosComSaida();
}