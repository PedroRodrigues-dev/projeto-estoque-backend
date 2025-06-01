package com.estoque.sistemaestoque.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimento_estoque")
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "tipo_movimentacao", nullable = false)
    private String tipoMovimentacao;

    @Column(name = "quantidade_movimentada", nullable = false)
    private Integer quantidadeMovimentada;

    @Column(name = "valor_venda", precision = 15, scale = 2)
    private BigDecimal valorVenda;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao;
}