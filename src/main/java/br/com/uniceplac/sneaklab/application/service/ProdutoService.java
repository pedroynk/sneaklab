package br.com.uniceplac.sneaklab.application.service;

import br.com.uniceplac.sneaklab.application.ports.in.CadastrarProdutoUseCase;
import br.com.uniceplac.sneaklab.application.ports.out.ProdutoRepositoryPort;
import br.com.uniceplac.sneaklab.domain.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService implements CadastrarProdutoUseCase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public ProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Produto criar(Produto produto) {
        // Regras de negócio simples de exemplo
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero.");
        }
        if (produto.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
        return produtoRepositoryPort.salvar(produto);
    }

    @Override
    public Produto atualizar(int id, Produto produtoAtualizado) {
        Produto existente = buscarPorId(id);

        existente.setNome(produtoAtualizado.getNome());
        existente.setSku(produtoAtualizado.getSku());
        existente.setPreco(produtoAtualizado.getPreco());
        existente.setEstoque(produtoAtualizado.getEstoque());

        return produtoRepositoryPort.salvar(existente);
    }

    @Override
    public List<Produto> listar() {
        return produtoRepositoryPort.listarTodos();
    }

    @Override
    public Produto buscarPorId(int id) {
        return produtoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com id: " + id));
    }

    @Override
    public void deletar(int id) {
        // Validação simples: garantir que existe antes de deletar
        buscarPorId(id);
        produtoRepositoryPort.deletarPorId(id);
    }
}
