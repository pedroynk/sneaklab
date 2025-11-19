package br.com.uniceplac.sneaklab.application.ports.out;

import br.com.uniceplac.sneaklab.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepositoryPort {

    Produto salvar(Produto produto);

    Optional<Produto> buscarPorId(int id);

    List<Produto> listarTodos();

    void deletarPorId(int id);
}
