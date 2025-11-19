package br.com.uniceplac.sneaklab.application.ports.out;

import br.com.uniceplac.sneaklab.domain.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryPort {

    Pedido salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(int id);

    List<Pedido> listarTodos();

    List<Pedido> listarPorCliente(int idCliente);

    void deletarPorId(int id);
}
