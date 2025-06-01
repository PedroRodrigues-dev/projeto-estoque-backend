package com.estoque.sistemaestoque.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.sistemaestoque.dtos.ProdutoDTO;
import com.estoque.sistemaestoque.dtos.ProdutoEstoqueDTO;
import com.estoque.sistemaestoque.dtos.ProdutoLucroDTO;
import com.estoque.sistemaestoque.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService service;

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar produto por código", description = "Retorna um produto pelo seu código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDTO> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(service.findByCodigo(codigo));
    }

    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ProdutoDTO> create(@RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipoId}")
    @Operation(summary = "Buscar produtos por tipo", description = "Retorna produtos de um tipo específico")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProdutoDTO>> buscarPorTipo(@PathVariable Integer tipoId) {
        return ResponseEntity.ok(service.buscarPorTipo(tipoId));
    }

    @GetMapping("/estoque-baixo")
    @Operation(summary = "Buscar produtos com estoque baixo", description = "Retorna produtos com quantidade abaixo do mínimo especificado")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosComEstoqueBaixo(@RequestParam Integer quantidadeMinima) {
        return ResponseEntity.ok(service.buscarProdutosComEstoqueBaixo(quantidadeMinima));
    }

    @GetMapping("/tipo/{tipoId}/estoque")
    @Operation(summary = "Consultar estoque por tipo", description = "Retorna produtos de um tipo específico com quantidade de saída e disponível")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
    public ResponseEntity<List<ProdutoEstoqueDTO>> consultarEstoquePorTipo(@PathVariable Long tipoId) {
        return ResponseEntity.ok(service.consultarEstoquePorTipo(tipoId));
    }

    @GetMapping("/lucro")
    @Operation(summary = "Consultar lucro por produto", description = "Retorna o lucro por produto, com quantidade total de saída e valores")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
    public ResponseEntity<List<ProdutoLucroDTO>> consultarLucroPorProduto() {
        return ResponseEntity.ok(service.consultarLucroPorProduto());
    }
}