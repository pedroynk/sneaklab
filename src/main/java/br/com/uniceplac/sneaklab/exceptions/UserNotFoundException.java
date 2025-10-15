package br.com.uniceplac.sneaklab.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado." + id);
    }
}
