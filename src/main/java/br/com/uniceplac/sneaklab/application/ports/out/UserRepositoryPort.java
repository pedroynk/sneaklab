package br.com.uniceplac.sneaklab.application.ports.out;

import br.com.uniceplac.sneaklab.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    User salvar(User user);

    Optional<User> buscarPorId(Long id);

    Optional<User> buscarPorEmail(String email);

    List<User> listarTodos();

    void deletarPorId(Long id);
}
