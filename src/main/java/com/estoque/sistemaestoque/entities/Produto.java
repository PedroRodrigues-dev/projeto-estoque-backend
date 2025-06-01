package com.estoque.sistemaestoque.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Código é obrigatório")
    @Column(nullable = false, length = 100, unique = true)
    private String codigo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tipo_produto_id", nullable = false)
    private TipoProduto tipoProduto;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @NotNull(message = "Valor do fornecedor é obrigatório")
    @Column(name = "valor_fornecedor", precision = 15, scale = 2)
    private BigDecimal valorFornecedor;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @Column
    private Boolean ativo = true;
}
