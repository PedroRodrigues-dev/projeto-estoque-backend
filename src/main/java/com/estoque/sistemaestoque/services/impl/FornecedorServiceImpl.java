package com.estoque.sistemaestoque.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estoque.sistemaestoque.dtos.FornecedorDTO;
import com.estoque.sistemaestoque.entities.Fornecedor;
import com.estoque.sistemaestoque.mappers.FornecedorMapper;
import com.estoque.sistemaestoque.repositories.FornecedorRepository;
import com.estoque.sistemaestoque.services.FornecedorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<FornecedorDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FornecedorDTO findById(Long id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        return mapper.toDTO(fornecedor);
    }

    @Override
    @Transactional
    public FornecedorDTO save(FornecedorDTO dto) {
        Fornecedor fornecedor = mapper.toEntity(dto);
        fornecedor = repository.save(fornecedor);
        return mapper.toDTO(fornecedor);
    }

    @Override
    @Transactional
    public FornecedorDTO update(Long id, FornecedorDTO dto) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

        fornecedor.setNome(dto.getNome());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefone(dto.getTelefone());

        fornecedor = repository.save(fornecedor);
        return mapper.toDTO(fornecedor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}