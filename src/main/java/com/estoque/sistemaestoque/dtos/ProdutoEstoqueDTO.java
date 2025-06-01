package com.estoque.sistemaestoque.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoqueDTO {
    private Long id;
    private String codigo;
    private String descricao;
    private String tipoProduto;
    private Integer quantidadeSaida;
    private Integer quantidadeDisponivel;
}