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
public class ProdutoLucroDTO {
    private Long id;
    private String codigo;
    private String descricao;
    private Integer quantidadeTotalSaida;
    private BigDecimal valorFornecedor;
    private BigDecimal valorVenda;
    private BigDecimal lucroUnitario;
    private BigDecimal lucroTotal;
}