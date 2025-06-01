package com.estoque.sistemaestoque.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.estoque.sistemaestoque.dtos.TipoProdutoDTO;
import com.estoque.sistemaestoque.entities.TipoProduto;
import com.estoque.sistemaestoque.mappers.TipoProdutoMapper;
import com.estoque.sistemaestoque.repositories.TipoProdutoRepository;
import com.estoque.sistemaestoque.services.impl.TipoProdutoServiceImpl;

@ExtendWith(MockitoExtension.class)
class TipoProdutoServiceTest {

    @Mock
    private TipoProdutoRepository tipoProdutoRepository;

    @Mock
    private TipoProdutoMapper mapper;

    @InjectMocks
    private TipoProdutoServiceImpl tipoProdutoService;

    private TipoProduto tipoProduto;
    private TipoProdutoDTO tipoProdutoDTO;

    @BeforeEach
    void setUp() {
        tipoProduto = new TipoProduto();
        tipoProduto.setId(1L);
        tipoProduto.setNome("Eletrônico");
        tipoProduto.setDescricao("Produtos eletrônicos");

        tipoProdutoDTO = new TipoProdutoDTO();
        tipoProdutoDTO.setId(1L);
        tipoProdutoDTO.setNome("Eletrônico");
        tipoProdutoDTO.setDescricao("Produtos eletrônicos");
    }

    @Test
    void deveSalvarTipoProduto() {
        when(mapper.toEntity(any(TipoProdutoDTO.class))).thenReturn(tipoProduto);
        when(tipoProdutoRepository.save(any(TipoProduto.class))).thenReturn(tipoProduto);
        when(mapper.toDTO(any(TipoProduto.class))).thenReturn(tipoProdutoDTO);

        TipoProdutoDTO resultado = tipoProdutoService.save(tipoProdutoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Eletrônico");
    }

    @Test
    void deveAtualizarTipoProduto() {
        when(tipoProdutoRepository.findById(anyLong())).thenReturn(Optional.of(tipoProduto));
        when(tipoProdutoRepository.save(any(TipoProduto.class))).thenReturn(tipoProduto);
        when(mapper.toDTO(any(TipoProduto.class))).thenReturn(tipoProdutoDTO);

        TipoProdutoDTO resultado = tipoProdutoService.update(1L, tipoProdutoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Eletrônico");
    }

    @Test
    void deveLancarExcecaoQuandoTipoProdutoNaoEncontrado() {
        when(tipoProdutoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tipoProdutoService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Tipo de produto não encontrado");
    }

    @Test
    void deveEncontrarTipoProdutoPorId() {
        when(tipoProdutoRepository.findById(anyLong())).thenReturn(Optional.of(tipoProduto));
        when(mapper.toDTO(any(TipoProduto.class))).thenReturn(tipoProdutoDTO);

        TipoProdutoDTO resultado = tipoProdutoService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void deveListarTodosTiposProduto() {
        when(tipoProdutoRepository.findAll()).thenReturn(List.of(tipoProduto));
        when(mapper.toDTO(any(TipoProduto.class))).thenReturn(tipoProdutoDTO);

        List<TipoProdutoDTO> resultado = tipoProdutoService.findAll();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNome()).isEqualTo("Eletrônico");
    }
}