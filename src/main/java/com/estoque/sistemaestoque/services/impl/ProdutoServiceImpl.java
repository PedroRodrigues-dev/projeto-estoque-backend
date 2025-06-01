package com.estoque.sistemaestoque.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estoque.sistemaestoque.dtos.ProdutoDTO;
import com.estoque.sistemaestoque.dtos.ProdutoEstoqueDTO;
import com.estoque.sistemaestoque.dtos.ProdutoLucroDTO;
import com.estoque.sistemaestoque.entities.MovimentoEstoque;
import com.estoque.sistemaestoque.entities.Produto;
import com.estoque.sistemaestoque.mappers.ProdutoMapper;
import com.estoque.sistemaestoque.repositories.MovimentoEstoqueRepository;
import com.estoque.sistemaestoque.repositories.ProdutoRepository;
import com.estoque.sistemaestoque.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final ProdutoMapper mapper;

    @Override
    @Transactional
    public ProdutoDTO save(ProdutoDTO dto) {
        Produto produto = mapper.toEntity(dto);
        produto = produtoRepository.save(produto);
        return mapper.toDTO(produto);
    }

    @Override
    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setCodigo(dto.getCodigo());
        produto.setDescricao(dto.getDescricao());
        produto.setValorFornecedor(dto.getValorFornecedor());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        produto.setAtivo(dto.getAtivo());

        produto = produtoRepository.save(produto);
        return mapper.toDTO(produto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapper.toDTO(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoDTO findByCodigo(String codigo) {
        Produto produto = produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapper.toDTO(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoDTO> buscarPorTipo(Integer tipoId) {
        return produtoRepository.findByTipoProdutoId(tipoId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoDTO> buscarProdutosComEstoqueBaixo(Integer quantidadeMinima) {
        return produtoRepository.findByEstoqueBaixo(quantidadeMinima).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoEstoqueDTO> consultarEstoquePorTipo(Long tipoId) {
        List<Produto> produtos = produtoRepository.findByTipoProdutoWithMovimentacoes(tipoId);

        return produtos.stream().map(produto -> {
            Integer quantidadeSaida = movimentoEstoqueRepository
                    .findByProdutoAndTipoMovimentacao(produto, "SAIDA")
                    .stream()
                    .mapToInt(m -> m.getQuantidadeMovimentada())
                    .sum();

            return ProdutoEstoqueDTO.builder()
                    .id(produto.getId())
                    .codigo(produto.getCodigo())
                    .descricao(produto.getDescricao())
                    .tipoProduto(produto.getTipoProduto().getNome())
                    .quantidadeSaida(quantidadeSaida)
                    .quantidadeDisponivel(produto.getQuantidadeEstoque())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoLucroDTO> consultarLucroPorProduto() {
        List<Produto> produtos = produtoRepository.findProdutosComSaida();

        return produtos.stream().map(produto -> {
            var movimentacoesSaida = movimentoEstoqueRepository
                    .findByProdutoAndTipoMovimentacao(produto, "SAIDA");

            Integer quantidadeTotalSaida = movimentacoesSaida.stream()
                    .mapToInt(MovimentoEstoque::getQuantidadeMovimentada)
                    .sum();

            BigDecimal totalVendas = movimentacoesSaida.stream()
                    .map(m -> m.getValorVenda().multiply(BigDecimal.valueOf(m.getQuantidadeMovimentada())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal custoTotal = produto.getValorFornecedor().multiply(BigDecimal.valueOf(quantidadeTotalSaida));
            BigDecimal lucroTotal = totalVendas.subtract(custoTotal);
            BigDecimal lucroUnitarioMedio = quantidadeTotalSaida > 0
                    ? lucroTotal.divide(BigDecimal.valueOf(quantidadeTotalSaida), RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            BigDecimal valorVendaMedio = quantidadeTotalSaida > 0
                    ? totalVendas.divide(BigDecimal.valueOf(quantidadeTotalSaida), RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            return ProdutoLucroDTO.builder()
                    .id(produto.getId())
                    .codigo(produto.getCodigo())
                    .descricao(produto.getDescricao())
                    .quantidadeTotalSaida(quantidadeTotalSaida)
                    .valorFornecedor(produto.getValorFornecedor())
                    .valorVenda(valorVendaMedio)
                    .lucroUnitario(lucroUnitarioMedio)
                    .lucroTotal(lucroTotal)
                    .build();
        }).collect(Collectors.toList());
    }

}