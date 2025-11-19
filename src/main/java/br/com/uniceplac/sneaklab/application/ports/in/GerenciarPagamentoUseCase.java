package br.com.uniceplac.sneaklab.application.ports.in;

import br.com.uniceplac.sneaklab.domain.Pagamento;
import br.com.uniceplac.sneaklab.domain.TipoPagamento;

import java.util.List;

public interface GerenciarPagamentoUseCase {

    //Registra um novo pagamento para um pedido em RASCUNHO.
    Pagamento registrarPagamento(long idPedido, double valor, TipoPagamento tipo);

    //Aprova um pagamento PENDENTE e marca o pedido como PAGO.
    Pagamento aprovarPagamento(long idPagamento);

    //Estorna um pagamento APROVADO e marca o pedido como CANCELADO.
    Pagamento estornarPagamento(long idPagamento);

    //Lista todos os pagamentos vinculados a um pedido.
    List<Pagamento> listarPorPedido(long idPedido);

    //Busca um pagamento específico pelo id.
    Pagamento buscarPorId(long idPagamento);
}
