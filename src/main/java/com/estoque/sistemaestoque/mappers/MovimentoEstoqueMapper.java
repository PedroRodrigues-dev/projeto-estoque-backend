package com.estoque.sistemaestoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.estoque.sistemaestoque.dtos.MovimentoEstoqueDTO;
import com.estoque.sistemaestoque.entities.MovimentoEstoque;

@Mapper(componentModel = "spring")
public interface MovimentoEstoqueMapper {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "tipoMovimentacao", target = "tipoMovimentacao")
    @Mapping(source = "dataMovimentacao", target = "dataMovimentacao")
    @Mapping(source = "quantidadeMovimentada", target = "quantidadeMovimentada")
    MovimentoEstoqueDTO toDTO(MovimentoEstoque entity);

    @Mapping(target = "produto.id", source = "produtoId")
    @Mapping(target = "tipoMovimentacao", source = "tipoMovimentacao")
    @Mapping(target = "dataMovimentacao", source = "dataMovimentacao")
    @Mapping(target = "quantidadeMovimentada", source = "quantidadeMovimentada")
    MovimentoEstoque toEntity(MovimentoEstoqueDTO dto);
}