package com.estoque.sistemaestoque.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 18)
    private String cnpj;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;
}