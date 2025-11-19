package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.application.ports.out.PedidoRepositoryPort;
import br.com.uniceplac.sneaklab.domain.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final PedidoSpringDataRepository springDataRepository;

    public PedidoRepositoryAdapter(PedidoSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity entity = toEntity(pedido);
        PedidoEntity salvo = springDataRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        return springDataRepository.findById((long) id)
                .map(this::toDomain);
    }

    @Override
    public List<Pedido> listarTodos() {
        return springDataRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> listarPorCliente(int idCliente) {
        return springDataRepository.findByIdCliente(idCliente)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(int id) {
        springDataRepository.deleteById((long) id);
    }

    private PedidoEntity toEntity(Pedido pedido) {
        Long id = pedido.getId() == 0 ? null : (long) pedido.getId();
        return new PedidoEntity(
                id,
                pedido.getIdCliente(),
                pedido.getData(),
                pedido.getTotal(),
                pedido.getStatus()
        );
    }

    private Pedido toDomain(PedidoEntity entity) {
        Pedido pedido = new Pedido();
        pedido.setId(entity.getId().intValue());
        pedido.setIdCliente(entity.getIdCliente());
        pedido.setData(entity.getData());
        pedido.setTotal(entity.getTotal());
        pedido.setStatus(entity.getStatus());
        return pedido;
    }
}
