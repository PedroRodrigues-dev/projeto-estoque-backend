package com.estoque.sistemaestoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.estoque.sistemaestoque.dtos.UsuarioDTO;
import com.estoque.sistemaestoque.entities.Usuario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {
    @Mapping(target = "senhaHash", source = "senha")
    Usuario toEntity(UsuarioDTO dto);

    @Mapping(target = "senha", ignore = true)
    UsuarioDTO toDTO(Usuario entity);
}