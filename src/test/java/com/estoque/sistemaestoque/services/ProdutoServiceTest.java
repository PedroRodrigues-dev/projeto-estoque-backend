package com.estoque.sistemaestoque.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.estoque.sistemaestoque.dtos.ProdutoDTO;
import com.estoque.sistemaestoque.dtos.ProdutoEstoqueDTO;
import com.estoque.sistemaestoque.dtos.ProdutoLucroDTO;
import com.estoque.sistemaestoque.entities.MovimentoEstoque;
import com.estoque.sistemaestoque.entities.Produto;
import com.estoque.sistemaestoque.entities.TipoProduto;
import com.estoque.sistemaestoque.entities.Fornecedor;
import com.estoque.sistemaestoque.mappers.ProdutoMapper;
import com.estoque.sistemaestoque.repositories.MovimentoEstoqueRepository;
import com.estoque.sistemaestoque.repositories.ProdutoRepository;
import com.estoque.sistemaestoque.repositories.TipoProdutoRepository;
import com.estoque.sistemaestoque.repositories.FornecedorRepository;
import com.estoque.sistemaestoque.services.impl.ProdutoServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Mock
    private TipoProdutoRepository tipoProdutoRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ProdutoMapper mapper;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    private Produto produto;
    private ProdutoDTO produtoDTO;
    private MovimentoEstoque movimento;
    private TipoProduto tipoProduto;
    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        tipoProduto = new TipoProduto();
        tipoProduto.setId(1L);
        tipoProduto.setNome("Eletrônico");

        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor Teste");

        produto = new Produto();
        produto.setId(1L);
        produto.setCodigo("PROD001");
        produto.setDescricao("Smartphone");
        produto.setTipoProduto(tipoProduto);
        produto.setFornecedor(fornecedor);
        produto.setValorFornecedor(new BigDecimal("1000.00"));
        produto.setQuantidadeEstoque(10);
        produto.setAtivo(true);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setCodigo("PROD001");
        produtoDTO.setDescricao("Smartphone");
        produtoDTO.setTipoProdutoId(1L);
        produtoDTO.setFornecedorId(1L);
        produtoDTO.setValorFornecedor(new BigDecimal("1000.00"));
        produtoDTO.setQuantidadeEstoque(10);
        produtoDTO.setAtivo(true);

        movimento = new MovimentoEstoque();
        movimento.setId(1L);
        movimento.setProduto(produto);
        movimento.setTipoMovimentacao("SAIDA");
        movimento.setQuantidadeMovimentada(2);
        movimento.setValorVenda(new BigDecimal("1500.00"));
        movimento.setDataMovimentacao(LocalDateTime.now());
    }

    @Test
    void deveSalvarProduto() {
        when(mapper.toEntity(any(ProdutoDTO.class))).thenReturn(produto);
        when(tipoProdutoRepository.findById(anyLong())).thenReturn(Optional.of(tipoProduto));
        when(fornecedorRepository.findById(anyLong())).thenReturn(Optional.of(fornecedor));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(mapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.save(produtoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodigo()).isEqualTo("PROD001");
    }

    @Test
    void deveAtualizarProduto() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(tipoProdutoRepository.findById(anyLong())).thenReturn(Optional.of(tipoProduto));
        when(fornecedorRepository.findById(anyLong())).thenReturn(Optional.of(fornecedor));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(mapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.update(1L, produtoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodigo()).isEqualTo("PROD001");
    }

    @Test
    void deveEncontrarProdutoPorId() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(mapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Produto não encontrado");
    }

    @Test
    void deveCalcularLucroPorProduto() {
        when(produtoRepository.findProdutosComSaida()).thenReturn(List.of(produto));
        when(movimentoEstoqueRepository.findByProdutoAndTipoMovimentacao(any(Produto.class), anyString()))
                .thenReturn(List.of(movimento));

        List<ProdutoLucroDTO> resultado = produtoService.consultarLucroPorProduto();

        assertThat(resultado).isNotEmpty();
        ProdutoLucroDTO lucroDTO = resultado.get(0);
        assertThat(lucroDTO.getQuantidadeTotalSaida()).isEqualTo(2);
        assertThat(lucroDTO.getLucroTotal()).isEqualByComparingTo(new BigDecimal("1000.00")); // (1500 - 1000) * 2
    }

    @Test
    void deveConsultarEstoquePorTipo() {
        when(produtoRepository.findByTipoProdutoWithMovimentacoes(anyLong())).thenReturn(List.of(produto));
        when(movimentoEstoqueRepository.findByProdutoAndTipoMovimentacao(any(Produto.class), anyString()))
                .thenReturn(List.of(movimento));

        List<ProdutoEstoqueDTO> resultado = produtoService.consultarEstoquePorTipo(1L);

        assertThat(resultado).isNotEmpty();
        ProdutoEstoqueDTO estoqueDTO = resultado.get(0);
        assertThat(estoqueDTO.getQuantidadeSaida()).isEqualTo(2);
        assertThat(estoqueDTO.getQuantidadeDisponivel()).isEqualTo(10);
    }
}