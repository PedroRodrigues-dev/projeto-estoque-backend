package com.estoque.sistemaestoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.estoque.sistemaestoque.dtos.TipoProdutoDTO;
import com.estoque.sistemaestoque.entities.TipoProduto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TipoProdutoMapper {
    TipoProduto toEntity(TipoProdutoDTO dto);

    TipoProdutoDTO toDTO(TipoProduto entity);
}