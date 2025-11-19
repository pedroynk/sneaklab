package br.com.uniceplac.sneaklab.application.service;

import br.com.uniceplac.sneaklab.application.ports.in.GerenciarPedidoUseCase;
import br.com.uniceplac.sneaklab.application.ports.out.PedidoRepositoryPort;
import br.com.uniceplac.sneaklab.application.ports.out.UserRepositoryPort;
import br.com.uniceplac.sneaklab.domain.Pedido;
import br.com.uniceplac.sneaklab.domain.StatusPedido;
import br.com.uniceplac.sneaklab.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PedidoService implements GerenciarPedidoUseCase {

    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final NotificationService notificationService;

    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort,
                         UserRepositoryPort userRepositoryPort,
                         NotificationService notificationService) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.notificationService = notificationService;
    }

    @Override
    public Pedido criar(Pedido pedido) {
        if (pedido.getData() == null) {
            pedido.setData(new Date());
        }
        if (pedido.getStatus() == null) {
            pedido.setStatus(StatusPedido.RASCUNHO);
        }

        Pedido salvo = pedidoRepositoryPort.salvar(pedido);

        // notifica status inicial do pedido (RASCUNHO)
        notificarStatusPedidoParaCliente(salvo);

        return salvo;
    }

    @Override
    public Pedido atualizar(Pedido pedidoAtualizado) {
        Pedido existente = buscarPorId((int) pedidoAtualizado.getId());

        if (pedidoAtualizado.getData() != null) {
            existente.setData(pedidoAtualizado.getData());
        }
        if (pedidoAtualizado.getTotal() != 0) {
            existente.setTotal(pedidoAtualizado.getTotal());
        }
        if (pedidoAtualizado.getStatus() != null &&
                pedidoAtualizado.getStatus() != existente.getStatus()) {
            existente.setStatus(pedidoAtualizado.getStatus());
        }

        Pedido salvo = pedidoRepositoryPort.salvar(existente);

        // sempre que atualizar, podemos notificar status (principalmente se mudou)
        notificarStatusPedidoParaCliente(salvo);

        return salvo;
    }

    @Override
    public Pedido atualizarStatus(int idPedido, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(idPedido);

        if (pedido.getStatus() == novoStatus) {
            return pedido; // idempotente
        }

        // Regras básicas de transição – adapta conforme seu diagrama de estados
        // Ex.: não ir de CANCELADO pra ENVIADO, etc.
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalArgumentException(
                    "Não é possível alterar o status de um pedido já cancelado."
            );
        }

        pedido.setStatus(novoStatus);
        Pedido salvo = pedidoRepositoryPort.salvar(pedido);

        notificarStatusPedidoParaCliente(salvo);

        return salvo;
    }

    @Override
    public Pedido cancelar(int idPedido) {
        Pedido pedido = buscarPorId(idPedido);

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            return pedido; // idempotente
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        Pedido salvo = pedidoRepositoryPort.salvar(pedido);

        notificarStatusPedidoParaCliente(salvo);

        return salvo;
    }

    @Override
    public Pedido buscarPorId(int id) {
        return pedidoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado. Id = " + id));
    }

    @Override
    public List<Pedido> listar() {
        return pedidoRepositoryPort.listarTodos();
    }

    // ---------------------- métodos auxiliares ----------------------

    private void notificarStatusPedidoParaCliente(Pedido pedido) {
        String email = buscarEmailDoCliente(pedido);
        if (email == null || email.isBlank()) {
            return;
        }
        notificationService.notificarStatusPedido(pedido, email);
    }

    /**
     * SUPOSIÇÃO: Pedido possui um campo idCliente (int) que mapeia para User.id.
     * Se você já alterou o domínio para usar userId (Long), troque aqui.
     */
    private String buscarEmailDoCliente(Pedido pedido) {
        int idCliente = (int) pedido.getIdCliente(); // ajuste se tiver outro campo
        return userRepositoryPort.buscarPorId((long) idCliente)
                .map(User::getEmail)
                .orElse(null);
    }
}
