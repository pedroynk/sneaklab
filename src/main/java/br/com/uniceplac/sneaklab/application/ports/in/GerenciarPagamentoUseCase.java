package br.com.uniceplac.sneaklab.application.ports.in;

import br.com.uniceplac.sneaklab.domain.Pagamento;
import br.com.uniceplac.sneaklab.domain.TipoPagamento;

import java.util.List;

public interface GerenciarPagamentoUseCase {

    //Registra um novo pagamento para um pedido em RASCUNHO.
    Pagamento registrarPagamento(int idPedido, double valor, TipoPagamento tipo);

    //Aprova um pagamento PENDENTE e marca o pedido como PAGO.
    Pagamento aprovarPagamento(int idPagamento);

    //Estorna um pagamento APROVADO e marca o pedido como CANCELADO.
    Pagamento estornarPagamento(int idPagamento);

    //Lista todos os pagamentos vinculados a um pedido.
    List<Pagamento> listarPorPedido(int idPedido);

    //Busca um pagamento específico pelo id.
    Pagamento buscarPorId(int idPagamento);
}
