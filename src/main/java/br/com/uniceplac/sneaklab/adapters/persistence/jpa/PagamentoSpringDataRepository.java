package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoSpringDataRepository extends JpaRepository<PagamentoEntity, Long> {

    List<PagamentoEntity> findByIdPedido(int idPedido);
}
