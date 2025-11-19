package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.application.ports.out.PagamentoRepositoryPort;
import br.com.uniceplac.sneaklab.domain.Pagamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PagamentoRepositoryAdapter implements PagamentoRepositoryPort {

    private final PagamentoSpringDataRepository springDataRepository;

    public PagamentoRepositoryAdapter(PagamentoSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        PagamentoEntity entity = toEntity(pagamento);
        PagamentoEntity salvo = springDataRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public Optional<Pagamento> buscarPorId(int id) {
        return springDataRepository.findById((long) id)
                .map(this::toDomain);
    }

    @Override
    public List<Pagamento> listarPorPedido(int idPedido) {
        return springDataRepository.findByIdPedido(idPedido).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private PagamentoEntity toEntity(Pagamento pagamento) {
        Long id = pagamento.getId() == 0 ? null : (long) pagamento.getId();
        return new PagamentoEntity(
                id,
                (int) pagamento.getIdPedido(),
                pagamento.getData(),
                pagamento.getValor(),
                pagamento.getTipo(),
                pagamento.getStatus()
        );
    }

    private Pagamento toDomain(PagamentoEntity entity) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(entity.getId().intValue());
        pagamento.setIdPedido(entity.getIdPedido());
        pagamento.setData(entity.getData());
        pagamento.setValor(entity.getValor());
        pagamento.setTipo(entity.getTipo());
        pagamento.setStatus(entity.getStatus());
        return pagamento;
    }
}
