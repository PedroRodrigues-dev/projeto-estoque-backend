package com.estoque.sistemaestoque.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.sistemaestoque.dtos.MovimentoEstoqueDTO;
import com.estoque.sistemaestoque.services.MovimentoEstoqueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movimentos")
@RequiredArgsConstructor
@Tag(name = "Movimentações", description = "Endpoints para gerenciamento de movimentações de estoque")
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService service;

    @GetMapping
    @Operation(summary = "Listar movimentações", description = "Retorna todas as movimentações de estoque")
    @ApiResponse(responseCode = "200", description = "Lista de movimentações retornada com sucesso")
    public ResponseEntity<List<MovimentoEstoqueDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar movimentação por ID", description = "Retorna uma movimentação específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação encontrada"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    public ResponseEntity<MovimentoEstoqueDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/produto/{produtoId}")
    @Operation(summary = "Buscar movimentações por produto", description = "Retorna todas as movimentações de um produto")
    @ApiResponse(responseCode = "200", description = "Lista de movimentações retornada com sucesso")
    public ResponseEntity<List<MovimentoEstoqueDTO>> findByProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(service.findByProduto(produtoId));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Buscar movimentações por período", description = "Retorna movimentações dentro do período especificado. Use o formato: yyyy-MM-ddTHH:mm:ss (exemplo: 2025-06-01T16:14:14)")
    @ApiResponse(responseCode = "200", description = "Lista de movimentações retornada com sucesso")
    public ResponseEntity<List<MovimentoEstoqueDTO>> findByPeriodo(
            @Parameter(description = "Data inicial no formato yyyy-MM-ddTHH:mm:ss", example = "2025-06-01T16:14:14") @RequestParam LocalDateTime inicio,
            @Parameter(description = "Data final no formato yyyy-MM-ddTHH:mm:ss", example = "2025-06-01T16:14:14") @RequestParam LocalDateTime fim) {
        return ResponseEntity.ok(service.findByPeriodo(inicio, fim));
    }

    @PostMapping
    @Operation(summary = "Registrar movimentação", description = "Registra uma nova movimentação de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<MovimentoEstoqueDTO> create(@Valid @RequestBody MovimentoEstoqueDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }
}