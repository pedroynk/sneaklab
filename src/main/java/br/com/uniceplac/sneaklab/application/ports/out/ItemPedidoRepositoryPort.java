package br.com.uniceplac.sneaklab.application.ports.out;

import br.com.uniceplac.sneaklab.domain.ItemPedido;

import java.util.List;

public interface ItemPedidoRepositoryPort {

    ItemPedido salvar(ItemPedido item);

    List<ItemPedido> listarPorPedido(int idPedido);

    void deletarPorPedido(int idPedido);
}
