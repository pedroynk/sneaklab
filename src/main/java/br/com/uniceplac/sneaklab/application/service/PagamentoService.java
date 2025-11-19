package br.com.uniceplac.sneaklab.application.service;

import br.com.uniceplac.sneaklab.application.ports.in.GerenciarPagamentoUseCase;
import br.com.uniceplac.sneaklab.application.ports.out.PagamentoRepositoryPort;
import br.com.uniceplac.sneaklab.application.ports.out.PedidoRepositoryPort;
import br.com.uniceplac.sneaklab.application.ports.out.UserRepositoryPort;
import br.com.uniceplac.sneaklab.domain.*;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PagamentoService implements GerenciarPagamentoUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final NotificationService notificationService;
    private final PagamentoRepositoryPort pagamentoRepositoryPort;
    private final PedidoRepositoryPort pedidoRepositoryPort;

    public PagamentoService(PagamentoRepositoryPort pagamentoRepositoryPort,
                            PedidoRepositoryPort pedidoRepositoryPort, UserRepositoryPort userRepositoryPort, NotificationService notificationService) {
        this.pagamentoRepositoryPort = pagamentoRepositoryPort;
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.notificationService = notificationService;
    }

    @Override
    public Pagamento registrarPagamento(int idPedido, double valor, TipoPagamento tipoPagamento) {
        Pedido pedido = pedidoRepositoryPort.buscarPorId(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: id=" + idPedido));

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Não é possível registrar pagamento para pedido CANCELADO.");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setIdPedido(idPedido);
        pagamento.setData(new Date());
        pagamento.setValor(valor);
        pagamento.setTipo(tipoPagamento);
        pagamento.setStatus(StatusPagamento.PENDENTE);

        Pagamento salvo = pagamentoRepositoryPort.salvar(pagamento);

        String email = buscarEmailDoCliente(pedido);
        notificationService.enviarNotificacaoGenerica(
                email,
                "[Sneaklab] Pagamento iniciado",
                """
                Olá!
                
                Registramos um novo pagamento para o pedido #%d no valor de R$ %.2f.
                Status atual: %s
                
                Assim que o pagamento for confirmado, você receberá uma nova notificação.
                
                Equipe Sneaklab.
                """.formatted(pedido.getId(), salvo.getValor(), salvo.getStatus())
        );

        return salvo;

    }

    @Override
    public Pagamento aprovarPagamento(int idPagamento) {
        Pagamento pagamento = pagamentoRepositoryPort.buscarPorId(idPagamento)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: id=" + idPagamento));

        if (pagamento.getStatus() == StatusPagamento.ESTORNADO) {
            throw new IllegalStateException("Não é possível aprovar um pagamento já ESTORNADO.");
        }

        if (pagamento.getStatus() == StatusPagamento.APROVADO) {
            // Idempotente: já aprovado, apenas retorna
            return pagamento;
        }

        Pedido pedido = pedidoRepositoryPort.buscarPorId((int) pagamento.getIdPedido())
                .orElseThrow(() -> new IllegalStateException(
                        "Pedido vinculado ao pagamento não encontrado: id=" + pagamento.getIdPedido()
                ));

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Não é possível aprovar pagamento de um pedido CANCELADO.");
        }

        // Atualiza status do pagamento
        pagamento.setStatus(StatusPagamento.APROVADO);
        Pagamento salvo = pagamentoRepositoryPort.salvar(pagamento);

        // Regra de negócio: se o pedido estava em RASCUNHO, vira PAGO
        if (pedido.getStatus() == StatusPedido.RASCUNHO) {
            pedido.setStatus(StatusPedido.PAGO);
            pedidoRepositoryPort.salvar(pedido);
        }
        // Notificações
        String email = buscarEmailDoCliente(pedido);
        notificationService.notificarPagamentoAprovado(salvo, email);
        notificationService.notificarStatusPedido(pedido, email);

        return salvo;
    }

    @Override
    public Pagamento estornarPagamento(int idPagamento) {
        Pagamento pagamento = pagamentoRepositoryPort.buscarPorId(idPagamento)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: id=" + idPagamento));

        if (pagamento.getStatus() == StatusPagamento.ESTORNADO) {
            // Idempotente: já estornado
            return pagamento;
        }

        Pedido pedido = pedidoRepositoryPort.buscarPorId((int) pagamento.getIdPedido())
                .orElseThrow(() -> new IllegalStateException(
                        "Pedido vinculado ao pagamento não encontrado: id=" + pagamento.getIdPedido()
                ));

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new IllegalStateException("Não é possível estornar pagamento de pedido ENTREGUE.");
        }

        // Atualiza statuses
        pagamento.setStatus(StatusPagamento.ESTORNADO);
        pedido.setStatus(StatusPedido.CANCELADO);
        Pagamento salvo = pagamentoRepositoryPort.salvar(pagamento);

        pedidoRepositoryPort.salvar(pedido);

        // Notificações
        String email = buscarEmailDoCliente(pedido);
        notificationService.notificarPagamentoEstornado(salvo, email);
        notificationService.notificarStatusPedido(pedido, email);

        return salvo;
    }

    @Override
    public Pagamento buscarPorId(int idPagamento) {
        return pagamentoRepositoryPort.buscarPorId(idPagamento)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: id=" + idPagamento));
    }


    @Override
    public List<Pagamento> listarPorPedido(int idPedido) {
        return pagamentoRepositoryPort.listarPorPedido(idPedido);
    }
    private String buscarEmailDoCliente(Pedido pedido) {
        try {
            Long userId = pedido.getIdCliente(); // <-- ajuste se o campo tiver outro nome
            if (userId == null) {
                return null;
            }
            return userRepositoryPort.buscarPorId(userId)
                    .map(User::getEmail)
                    .orElse(null);
        } catch (NoSuchMethodError e) {
            // Caso o Pedido ainda não tenha getUserId, ajuste o método de acordo com seu modelo
            return null;
        }
    }
}
