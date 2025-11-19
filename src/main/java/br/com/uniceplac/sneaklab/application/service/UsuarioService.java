package br.com.uniceplac.sneaklab.application.service;

import br.com.uniceplac.sneaklab.application.ports.in.GerenciarUsuarioUseCase;
import br.com.uniceplac.sneaklab.application.ports.out.UserRepositoryPort;
import br.com.uniceplac.sneaklab.domain.User;
import br.com.uniceplac.sneaklab.domain.UserRole;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UsuarioService implements GerenciarUsuarioUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final NotificationService notificationService;

    public UsuarioService(UserRepositoryPort userRepositoryPort,
                          NotificationService notificationService) {
        this.userRepositoryPort = userRepositoryPort;
        this.notificationService = notificationService;
    }

    @Override
    public User criar(User user) {
        validarNovoUsuario(user);

        userRepositoryPort.buscarPorEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Já existe usuário com esse e-mail.");
                });

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(Instant.now());
        }
        user.setUpdatedAt(user.getCreatedAt());

        User salvo = userRepositoryPort.salvar(user);

        // Notificação de boas-vindas
        notificationService.enviarNotificacaoGenerica(
                salvo.getEmail(),
                "[Sneaklab] Bem-vindo(a), " + salvo.getName(),
                """
                Olá, %s!
                
                Sua conta na Sneaklab foi criada com sucesso.
                Agora você já pode acompanhar seus pedidos e pagamentos pelo nosso sistema.
                
                Qualquer dúvida, estamos à disposição.
                
                Equipe Sneaklab.
                """.formatted(salvo.getName())
        );

        return salvo;
    }

    @Override
    public User atualizar(Long id, User userAtualizado) {
        User existente = buscarPorId(id);

        boolean emailAlterado = false;
        String emailAnterior = existente.getEmail();

        if (userAtualizado.getName() != null) {
            existente.setName(userAtualizado.getName());
        }

        if (userAtualizado.getEmail() != null &&
                !userAtualizado.getEmail().equals(existente.getEmail())) {
            userRepositoryPort.buscarPorEmail(userAtualizado.getEmail())
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("Já existe usuário com esse e-mail.");
                    });
            existente.setEmail(userAtualizado.getEmail());
            emailAlterado = true;
        }

        if (userAtualizado.getPasswordHash() != null) {
            existente.setPasswordHash(userAtualizado.getPasswordHash());
        }

        if (userAtualizado.getRole() != null) {
            existente.setRole(userAtualizado.getRole());
        }

        existente.setUpdatedAt(Instant.now());
        User salvo = userRepositoryPort.salvar(existente);

        // Opcional: notificar se o e-mail foi alterado
        if (emailAlterado) {
            notificationService.enviarNotificacaoGenerica(
                    salvo.getEmail(),
                    "[Sneaklab] E-mail da conta atualizado",
                    """
                    Olá, %s!
                    
                    O e-mail da sua conta foi atualizado de:
                    %s
                    para:
                    %s
                    
                    Se você não reconhece esta alteração, entre em contato com o suporte.
                    
                    Equipe Sneaklab.
                    """.formatted(salvo.getName(), emailAnterior, salvo.getEmail())
            );
        }

        return salvo;
    }

    @Override
    public List<User> listar() {
        return userRepositoryPort.listarTodos();
    }

    @Override
    public User buscarPorId(Long id) {
        return userRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado. Id = " + id));
    }

    @Override
    public void deletar(Long id) {
        // garante que existe
        User existente = buscarPorId(id);

        userRepositoryPort.deletarPorId(id);

        // Opcional: notificar exclusão da conta
        notificationService.enviarNotificacaoGenerica(
                existente.getEmail(),
                "[Sneaklab] Conta removida",
                """
                Olá, %s!
                
                Sua conta na Sneaklab foi removida.
                Se isso não foi solicitado por você, entre em contato com o suporte imediatamente.
                
                Equipe Sneaklab.
                """.formatted(existente.getName())
        );
    }

    private void validarNovoUsuario(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role é obrigatória (ADMIN, CLIENTE ou VENDEDOR).");
        }
    }
}
