package com.estoque.sistemaestoque.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estoque.sistemaestoque.dtos.TipoProdutoDTO;
import com.estoque.sistemaestoque.entities.TipoProduto;
import com.estoque.sistemaestoque.mappers.TipoProdutoMapper;
import com.estoque.sistemaestoque.repositories.TipoProdutoRepository;
import com.estoque.sistemaestoque.services.TipoProdutoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoProdutoServiceImpl implements TipoProdutoService {

    private final TipoProdutoRepository repository;
    private final TipoProdutoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<TipoProdutoDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TipoProdutoDTO findById(Long id) {
        TipoProduto tipoProduto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de produto não encontrado"));
        return mapper.toDTO(tipoProduto);
    }

    @Override
    @Transactional
    public TipoProdutoDTO save(TipoProdutoDTO dto) {
        TipoProduto tipoProduto = mapper.toEntity(dto);
        tipoProduto = repository.save(tipoProduto);
        return mapper.toDTO(tipoProduto);
    }

    @Override
    @Transactional
    public TipoProdutoDTO update(Long id, TipoProdutoDTO dto) {
        TipoProduto tipoProduto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de produto não encontrado"));

        tipoProduto.setNome(dto.getNome());
        tipoProduto.setDescricao(dto.getDescricao());

        tipoProduto = repository.save(tipoProduto);
        return mapper.toDTO(tipoProduto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}