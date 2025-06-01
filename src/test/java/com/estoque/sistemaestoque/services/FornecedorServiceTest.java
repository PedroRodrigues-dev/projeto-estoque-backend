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

import com.estoque.sistemaestoque.dtos.FornecedorDTO;
import com.estoque.sistemaestoque.entities.Fornecedor;
import com.estoque.sistemaestoque.mappers.FornecedorMapper;
import com.estoque.sistemaestoque.repositories.FornecedorRepository;
import com.estoque.sistemaestoque.services.impl.FornecedorServiceImpl;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private FornecedorMapper mapper;

    @InjectMocks
    private FornecedorServiceImpl fornecedorService;

    private Fornecedor fornecedor;
    private FornecedorDTO fornecedorDTO;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setCnpj("12.345.678/0001-90");
        fornecedor.setEmail("fornecedor@teste.com");
        fornecedor.setTelefone("(11) 99999-9999");

        fornecedorDTO = new FornecedorDTO();
        fornecedorDTO.setId(1L);
        fornecedorDTO.setNome("Fornecedor Teste");
        fornecedorDTO.setCnpj("12.345.678/0001-90");
        fornecedorDTO.setEmail("fornecedor@teste.com");
        fornecedorDTO.setTelefone("(11) 99999-9999");
    }

    @Test
    void deveSalvarFornecedor() {
        when(mapper.toEntity(any(FornecedorDTO.class))).thenReturn(fornecedor);
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);
        when(mapper.toDTO(any(Fornecedor.class))).thenReturn(fornecedorDTO);

        FornecedorDTO resultado = fornecedorService.save(fornecedorDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Fornecedor Teste");
        assertThat(resultado.getCnpj()).isEqualTo("12.345.678/0001-90");
    }

    @Test
    void deveAtualizarFornecedor() {
        when(fornecedorRepository.findById(anyLong())).thenReturn(Optional.of(fornecedor));
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);
        when(mapper.toDTO(any(Fornecedor.class))).thenReturn(fornecedorDTO);

        FornecedorDTO resultado = fornecedorService.update(1L, fornecedorDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Fornecedor Teste");
    }

    @Test
    void deveLancarExcecaoQuandoFornecedorNaoEncontrado() {
        when(fornecedorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fornecedorService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Fornecedor não encontrado");
    }

    @Test
    void deveEncontrarFornecedorPorId() {
        when(fornecedorRepository.findById(anyLong())).thenReturn(Optional.of(fornecedor));
        when(mapper.toDTO(any(Fornecedor.class))).thenReturn(fornecedorDTO);

        FornecedorDTO resultado = fornecedorService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void deveListarTodosFornecedores() {
        when(fornecedorRepository.findAll()).thenReturn(List.of(fornecedor));
        when(mapper.toDTO(any(Fornecedor.class))).thenReturn(fornecedorDTO);

        List<FornecedorDTO> resultado = fornecedorService.findAll();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNome()).isEqualTo("Fornecedor Teste");
    }
}