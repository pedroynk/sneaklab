package br.com.uniceplac.sneaklab.application.ports.in;

import br.com.uniceplac.sneaklab.domain.Pedido;

import java.util.List;

public interface GerenciarPedidoUseCase {

    Pedido criarPedido(long idCliente);

    Pedido adicionarItem(long idPedido, long idProduto, int quantidade);

    List<Pedido> listarPedidos();

    List<Pedido> listarPedidosPorCliente(long idCliente);

    Pedido buscarPorId(long id);

    Pedido enviarPedido(long id);

    Pedido confirmarEntrega(long id);

    Pedido cancelarPedido(long id);
}
