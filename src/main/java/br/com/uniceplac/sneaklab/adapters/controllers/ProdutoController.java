package br.com.uniceplac.sneaklab.adapters.controllers;

import br.com.uniceplac.sneaklab.adapters.dtos.ProdutoDto;
import br.com.uniceplac.sneaklab.application.ports.in.CadastrarProdutoUseCase;
import br.com.uniceplac.sneaklab.domain.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;

    public ProdutoController(CadastrarProdutoUseCase cadastrarProdutoUseCase) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto criar(@RequestBody ProdutoDto produtoDto) {
        Produto criado = cadastrarProdutoUseCase.criar(produtoDto.toDomain());
        return ProdutoDto.fromDomain(criado);
    }

    @GetMapping
    public List<ProdutoDto> listar() {
        return cadastrarProdutoUseCase.listar()
                .stream()
                .map(ProdutoDto::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProdutoDto buscarPorId(@PathVariable int id) {
        Produto produto = cadastrarProdutoUseCase.buscarPorId(id);
        return ProdutoDto.fromDomain(produto);
    }

    @PutMapping("/{id}")
    public ProdutoDto atualizar(@PathVariable int id, @RequestBody ProdutoDto produtoDto) {
        Produto atualizado = cadastrarProdutoUseCase.atualizar(id, produtoDto.toDomain());
        return ProdutoDto.fromDomain(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        cadastrarProdutoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
