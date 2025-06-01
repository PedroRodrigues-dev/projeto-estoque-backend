package com.estoque.sistemaestoque.dtos;

import lombok.Data;

@Data
public class FornecedorDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
}