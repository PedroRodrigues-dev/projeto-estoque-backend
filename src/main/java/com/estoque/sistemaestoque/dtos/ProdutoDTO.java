package com.estoque.sistemaestoque.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String codigo;
    private String descricao;
    private Long tipoProdutoId;
    private Long fornecedorId;
    private BigDecimal valorFornecedor;
    private Integer quantidadeEstoque;
    private Boolean ativo;
}