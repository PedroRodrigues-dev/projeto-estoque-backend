package com.estoque.sistemaestoque.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoqueDTO {
    private Long id;
    private Long produtoId;
    private String tipoMovimentacao;
    private Integer quantidadeMovimentada;
    private BigDecimal valorVenda;
    private LocalDateTime dataMovimentacao;
    private ProdutoDTO produto;
}