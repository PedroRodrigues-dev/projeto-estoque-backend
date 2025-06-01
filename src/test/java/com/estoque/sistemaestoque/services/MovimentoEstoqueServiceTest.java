package com.estoque.sistemaestoque.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.estoque.sistemaestoque.dtos.MovimentoEstoqueDTO;
import com.estoque.sistemaestoque.entities.MovimentoEstoque;
import com.estoque.sistemaestoque.entities.Produto;
import com.estoque.sistemaestoque.mappers.MovimentoEstoqueMapper;
import com.estoque.sistemaestoque.repositories.MovimentoEstoqueRepository;
import com.estoque.sistemaestoque.repositories.ProdutoRepository;
import com.estoque.sistemaestoque.services.impl.MovimentoEstoqueServiceImpl;

@ExtendWith(MockitoExtension.class)
class MovimentoEstoqueServiceTest {

    @Mock
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentoEstoqueMapper mapper;

    @InjectMocks
    private MovimentoEstoqueServiceImpl movimentoEstoqueService;

    private MovimentoEstoque movimento;
    private MovimentoEstoqueDTO movimentoDTO;
    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setCodigo("PROD001");
        produto.setDescricao("Smartphone");
        produto.setValorFornecedor(new BigDecimal("1000.00"));
        produto.setQuantidadeEstoque(10);
        produto.setAtivo(true);

        movimento = new MovimentoEstoque();
        movimento.setId(1L);
        movimento.setProduto(produto);
        movimento.setTipoMovimentacao("SAIDA");
        movimento.setQuantidadeMovimentada(2);
        movimento.setValorVenda(new BigDecimal("1500.00"));
        movimento.setDataMovimentacao(LocalDateTime.now());

        movimentoDTO = new MovimentoEstoqueDTO();
        movimentoDTO.setId(1L);
        movimentoDTO.setProdutoId(1L);
        movimentoDTO.setTipoMovimentacao("SAIDA");
        movimentoDTO.setQuantidadeMovimentada(2);
        movimentoDTO.setValorVenda(new BigDecimal("1500.00"));
        movimentoDTO.setDataMovimentacao(LocalDateTime.now());
    }

    @Test
    void deveSalvarMovimentacao() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(mapper.toEntity(any(MovimentoEstoqueDTO.class))).thenReturn(movimento);
        when(movimentoEstoqueRepository.save(any(MovimentoEstoque.class))).thenReturn(movimento);
        when(mapper.toDTO(any(MovimentoEstoque.class))).thenReturn(movimentoDTO);

        MovimentoEstoqueDTO resultado = movimentoEstoqueService.save(movimentoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTipoMovimentacao()).isEqualTo("SAIDA");
        assertThat(resultado.getQuantidadeMovimentada()).isEqualTo(2);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movimentoEstoqueService.save(movimentoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Produto não encontrado");
    }

    @Test
    void deveEncontrarMovimentacaoPorId() {
        when(movimentoEstoqueRepository.findById(anyLong())).thenReturn(Optional.of(movimento));
        when(mapper.toDTO(any(MovimentoEstoque.class))).thenReturn(movimentoDTO);

        MovimentoEstoqueDTO resultado = movimentoEstoqueService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void deveListarMovimentacoesPorProduto() {
        when(movimentoEstoqueRepository.findByProdutoId(anyLong())).thenReturn(List.of(movimento));
        when(mapper.toDTO(any(MovimentoEstoque.class))).thenReturn(movimentoDTO);

        List<MovimentoEstoqueDTO> resultado = movimentoEstoqueService.findByProduto(1L);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getProdutoId()).isEqualTo(1L);
    }

    @Test
    void deveListarMovimentacoesPorPeriodo() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now();

        when(movimentoEstoqueRepository.findByDataMovimentacaoBetween(any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(List.of(movimento));
        when(mapper.toDTO(any(MovimentoEstoque.class))).thenReturn(movimentoDTO);

        List<MovimentoEstoqueDTO> resultado = movimentoEstoqueService.findByPeriodo(inicio, fim);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getValorVenda()).isEqualByComparingTo(new BigDecimal("1500.00"));
    }
}