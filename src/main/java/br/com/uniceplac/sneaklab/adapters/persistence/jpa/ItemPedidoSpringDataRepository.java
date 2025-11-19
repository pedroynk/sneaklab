package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoSpringDataRepository extends JpaRepository<ItemPedidoEntity, Long> {

    List<ItemPedidoEntity> findByIdPedido(int idPedido);

    void deleteByIdPedido(int idPedido);
}
