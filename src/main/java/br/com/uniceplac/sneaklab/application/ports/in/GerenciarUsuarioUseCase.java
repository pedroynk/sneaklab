package br.com.uniceplac.sneaklab.application.ports.in;

import br.com.uniceplac.sneaklab.domain.User;

import java.util.List;

public interface GerenciarUsuarioUseCase {

    User criar(User user);

    User atualizar(Long id, User userAtualizado);

    List<User> listar();

    User buscarPorId(Long id);

    void deletar(Long id);
}
