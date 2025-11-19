package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoSpringDataRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByIdCliente(int idCliente);
}
