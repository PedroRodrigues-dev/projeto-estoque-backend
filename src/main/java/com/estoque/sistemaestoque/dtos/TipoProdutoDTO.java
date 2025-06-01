package com.estoque.sistemaestoque.dtos;

import lombok.Data;

@Data
public class TipoProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
}