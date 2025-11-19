package br.com.uniceplac.sneaklab.application.ports.in;

import br.com.uniceplac.sneaklab.domain.Pedido;

import java.util.List;

public interface GerenciarPedidoUseCase {

    Pedido criarPedido(int idCliente);

    Pedido adicionarItem(int idPedido, int idProduto, int quantidade);

    List<Pedido> listarPedidos();

    List<Pedido> listarPedidosPorCliente(int idCliente);

    Pedido buscarPorId(int id);

    Pedido enviarPedido(int id);

    Pedido confirmarEntrega(int id);

    Pedido cancelarPedido(int id);
}
