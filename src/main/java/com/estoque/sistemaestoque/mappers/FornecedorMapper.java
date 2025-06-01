package com.estoque.sistemaestoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.estoque.sistemaestoque.dtos.FornecedorDTO;
import com.estoque.sistemaestoque.entities.Fornecedor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FornecedorMapper {
    Fornecedor toEntity(FornecedorDTO dto);

    FornecedorDTO toDTO(Fornecedor entity);
}