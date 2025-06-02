package com.estoque.sistemaestoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.estoque.sistemaestoque.dtos.ProdutoDTO;
import com.estoque.sistemaestoque.entities.Produto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TipoProdutoMapper.class,
        FornecedorMapper.class })
public interface ProdutoMapper {
    @Mapping(target = "tipoProduto.id", source = "tipoProdutoId")
    @Mapping(target = "fornecedor.id", source = "fornecedorId")
    Produto toEntity(ProdutoDTO dto);

    @Mapping(target = "tipoProdutoId", source = "tipoProduto.id")
    @Mapping(target = "fornecedorId", source = "fornecedor.id")
    @Mapping(target = "tipoProduto", source = "tipoProduto")
    @Mapping(target = "fornecedor", source = "fornecedor")
    ProdutoDTO toDTO(Produto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoProduto", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    void updateEntityFromDto(ProdutoDTO dto, @MappingTarget Produto entity);
}