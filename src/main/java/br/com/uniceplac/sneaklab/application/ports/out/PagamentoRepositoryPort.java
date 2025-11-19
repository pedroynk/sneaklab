package br.com.uniceplac.sneaklab.application.ports.out;

import br.com.uniceplac.sneaklab.domain.Pagamento;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepositoryPort {

    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> buscarPorId(int id);

    List<Pagamento> listarPorPedido(int idPedido);
}
