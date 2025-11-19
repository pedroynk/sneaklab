package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoSpringDataRepository extends JpaRepository<ProdutoEntity, Long> {
}
