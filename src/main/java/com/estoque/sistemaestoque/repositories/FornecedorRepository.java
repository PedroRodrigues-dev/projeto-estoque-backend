package com.estoque.sistemaestoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estoque.sistemaestoque.entities.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}