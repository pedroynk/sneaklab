package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.application.ports.out.ProdutoRepositoryPort;
import br.com.uniceplac.sneaklab.domain.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProdutoRepositoryAdapter implements ProdutoRepositoryPort {

    private final ProdutoSpringDataRepository springDataRepository;

    public ProdutoRepositoryAdapter(ProdutoSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = toEntity(produto);
        ProdutoEntity salvo = springDataRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public Optional<Produto> buscarPorId(int id) {
        return springDataRepository.findById((long) id)
                .map(this::toDomain);
    }

    @Override
    public List<Produto> listarTodos() {
        return springDataRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(int id) {
        springDataRepository.deleteById((long) id);
    }

    private ProdutoEntity toEntity(Produto produto) {
        Long id = produto.getId() == 0 ? null : (long) produto.getId();
        return new ProdutoEntity(
                id,
                produto.getNome(),
                produto.getSku(),
                produto.getPreco(),
                produto.getEstoque()
        );
    }

    private Produto toDomain(ProdutoEntity entity) {
        Produto produto = new Produto();
        produto.setId(entity.getId().intValue());
        produto.setNome(entity.getNome());
        produto.setSku(entity.getSku());
        produto.setPreco(entity.getPreco());
        produto.setEstoque(entity.getEstoque());
        return produto;
    }
}
