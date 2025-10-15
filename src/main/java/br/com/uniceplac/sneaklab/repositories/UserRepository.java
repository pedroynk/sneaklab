package br.com.uniceplac.sneaklab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uniceplac.sneaklab.models.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
