package br.com.uniceplac.sneaklab.application.ports.in;

import br.com.uniceplac.sneaklab.domain.Produto;

import java.util.List;

public interface CadastrarProdutoUseCase {

    
    Produto criar(Produto produto);

    Produto atualizar(int id, Produto produtoAtualizado);

    List<Produto> listar();

    Produto buscarPorId(int id);

    void deletar(int id);
}
