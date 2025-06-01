package com.estoque.sistemaestoque.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estoque.sistemaestoque.dtos.MovimentoEstoqueDTO;
import com.estoque.sistemaestoque.entities.MovimentoEstoque;
import com.estoque.sistemaestoque.entities.Produto;
import com.estoque.sistemaestoque.mappers.MovimentoEstoqueMapper;
import com.estoque.sistemaestoque.repositories.MovimentoEstoqueRepository;
import com.estoque.sistemaestoque.repositories.ProdutoRepository;
import com.estoque.sistemaestoque.services.MovimentoEstoqueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentoEstoqueServiceImpl implements MovimentoEstoqueService {

    private final MovimentoEstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueMapper mapper;

    @Override
    @Transactional
    public MovimentoEstoqueDTO save(MovimentoEstoqueDTO dto) {
        MovimentoEstoque movimento = mapper.toEntity(dto);

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if ("ENTRADA".equals(dto.getTipoMovimentacao())) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.getQuantidadeMovimentada());
        } else if ("SAIDA".equals(dto.getTipoMovimentacao())) {
            if (produto.getQuantidadeEstoque() < dto.getQuantidadeMovimentada()) {
                throw new RuntimeException("Quantidade insuficiente em estoque");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.getQuantidadeMovimentada());
        }

        produtoRepository.save(produto);
        movimento = repository.save(movimento);
        return mapper.toDTO(movimento);
    }

    @Override
    @Transactional(readOnly = true)
    public MovimentoEstoqueDTO findById(Long id) {
        MovimentoEstoque movimento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimento não encontrado"));
        return mapper.toDTO(movimento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimentoEstoqueDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimentoEstoqueDTO> findByProduto(Long produtoId) {
        return repository.findByProdutoId(produtoId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimentoEstoqueDTO> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataMovimentacaoBetween(inicio, fim).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}