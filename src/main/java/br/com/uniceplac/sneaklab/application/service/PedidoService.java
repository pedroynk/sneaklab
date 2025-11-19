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
import java.util.stream.Collectors;

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

    // ================== MÉTODOS DA INTERFACE ==================

    @Override
    public Pedido criarPedido(int idCliente) {
        Pedido pedido = new Pedido();
        pedido.setIdCliente(idCliente);
        pedido.setData(new Date());
        pedido.setStatus(StatusPedido.RASCUNHO);
        pedido.setTotal(0.0);

        Pedido salvo = pedidoRepositoryPort.salvar(pedido);
        notificarStatusPedidoParaCliente(salvo);
        return salvo;
    }

    @Override
    public Pedido adicionarItem(int idPedido, int idProduto, int quantidade) {
        // Por enquanto, só garante que o pedido existe.
        // Depois você pode integrar com ProdutoService + ItemPedidoRepositoryPort
        // para:
        //  - buscar preço do produto
        //  - criar ItemPedido
        //  - atualizar total do pedido
        Pedido pedido = buscarPorId(idPedido);

        // TODO: implementar regra de adicionar item ao pedido e recalcular total

        // Após atualizar o pedido no banco, notificar o cliente (se fizer sentido)
        notificarStatusPedidoParaCliente(pedido);
        return pedido;
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepositoryPort.listarTodos();
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(int idCliente) {
        // Implementação em memória (sem exigir novo método no repositório)
        return pedidoRepositoryPort.listarTodos()
                .stream()
                .filter(p -> p.getIdCliente() == idCliente)
                .collect(Collectors.toList());
    }

    @Override
    public Pedido buscarPorId(int id) {
        return pedidoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado. Id = " + id));
    }

    @Override
    public Pedido enviarPedido(int id) {
        return atualizarStatus(id, StatusPedido.ENVIADO);
    }

    @Override
    public Pedido confirmarEntrega(int id) {
        return atualizarStatus(id, StatusPedido.ENTREGUE);
    }

    @Override
    public Pedido cancelarPedido(int id) {
        return cancelar(id);
    }

    // ================== MÉTODOS AUXILIARES DE REGRA ==================

    /**
     * Atualiza campos genéricos de um pedido já existente.
     * Útil se você tiver um use case de atualização "full" depois.
     */
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
        notificarStatusPedidoParaCliente(salvo);
        return salvo;
    }

    /**
     * Regra central para mudança de status.
     */
    private Pedido atualizarStatus(int idPedido, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(idPedido);

        if (pedido.getStatus() == novoStatus) {
            // idempotente
            return pedido;
        }

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

    /**
     * Regra de cancelamento.
     */
    private Pedido cancelar(int idPedido) {
        Pedido pedido = buscarPorId(idPedido);

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            // idempotente
            return pedido;
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        Pedido salvo = pedidoRepositoryPort.salvar(pedido);

        notificarStatusPedidoParaCliente(salvo);
        return salvo;
    }

    // ================== NOTIFICAÇÃO ==================

    private void notificarStatusPedidoParaCliente(Pedido pedido) {
        String email = buscarEmailDoCliente(pedido);
        if (email == null || email.isBlank()) {
            return;
        }
        notificationService.notificarStatusPedido(pedido, email);
    }

    /**
     * SUPOSIÇÃO: Pedido possui um campo int idCliente que mapeia para User.id (Long).
     */
    private String buscarEmailDoCliente(Pedido pedido) {
        int idCliente = (int) pedido.getIdCliente();
        return userRepositoryPort.buscarPorId((long) idCliente)
                .map(User::getEmail)
                .orElse(null);
    }
}
