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
public class RelatorioLucroDTO {
    private Long produtoId;
    private String nomeProduto;
    private Integer quantidadeSaida;
    private BigDecimal valorTotalVenda;
    private BigDecimal valorTotalCusto;
    private BigDecimal lucroTotal;
}