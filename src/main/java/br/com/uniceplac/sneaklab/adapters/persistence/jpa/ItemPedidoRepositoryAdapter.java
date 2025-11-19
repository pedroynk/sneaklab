package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.application.ports.out.ItemPedidoRepositoryPort;
import br.com.uniceplac.sneaklab.domain.ItemPedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPedidoRepositoryAdapter implements ItemPedidoRepositoryPort {

    private final ItemPedidoSpringDataRepository springDataRepository;

    public ItemPedidoRepositoryAdapter(ItemPedidoSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public ItemPedido salvar(ItemPedido item) {
        ItemPedidoEntity entity = toEntity(item);
        ItemPedidoEntity salvo = springDataRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public List<ItemPedido> listarPorPedido(int idPedido) {
        return springDataRepository.findByIdPedido(idPedido)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorPedido(int idPedido) {
        springDataRepository.deleteByIdPedido(idPedido);
    }

    private ItemPedidoEntity toEntity(ItemPedido item) {
        Long id = item.getId() == 0 ? null : (long) item.getId();
        return new ItemPedidoEntity(
                id,
                (int) item.getIdPedido(),
                (int) item.getIdProduto(),
                item.getQuantidade(),
                item.getPrecounit(),
                item.getSubtotal()
        );
    }

    private ItemPedido toDomain(ItemPedidoEntity entity) {
        ItemPedido item = new ItemPedido();
        item.setId(entity.getId().intValue());
        item.setIdPedido(entity.getIdPedido());
        item.setIdProduto(entity.getIdProduto());
        item.setQuantidade(entity.getQuantidade());
        item.setPrecounit(entity.getPrecounit());
        item.setSubtotal(entity.getSubtotal());
        return item;
    }
}
