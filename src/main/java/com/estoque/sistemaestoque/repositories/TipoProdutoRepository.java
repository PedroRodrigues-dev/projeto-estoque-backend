package com.estoque.sistemaestoque.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.estoque.sistemaestoque.entities.TipoProduto;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long> {
    Optional<TipoProduto> findByNome(String nome);
}