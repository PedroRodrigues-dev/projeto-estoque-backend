package com.estoque.sistemaestoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.estoque.sistemaestoque.dtos.ProdutoDTO;
import com.estoque.sistemaestoque.entities.Produto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {
    @Mapping(target = "tipoProduto.id", source = "tipoProdutoId")
    @Mapping(target = "fornecedor.id", source = "fornecedorId")
    Produto toEntity(ProdutoDTO dto);

    @Mapping(target = "tipoProdutoId", source = "tipoProduto.id")
    @Mapping(target = "fornecedorId", source = "fornecedor.id")
    ProdutoDTO toDTO(Produto entity);
}