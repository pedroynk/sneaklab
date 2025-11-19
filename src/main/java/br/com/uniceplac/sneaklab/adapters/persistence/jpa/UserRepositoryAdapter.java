package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.application.ports.out.UserRepositoryPort;
import br.com.uniceplac.sneaklab.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserSpringDataRepository springDataRepository;

    public UserRepositoryAdapter(UserSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public User salvar(User user) {
        UserEntity entity = toEntity(user);
        UserEntity salvo = springDataRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public Optional<User> buscarPorId(Long id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<User> buscarPorEmail(String email) {
        return springDataRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<User> listarTodos() {
        return springDataRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        springDataRepository.deleteById(id);
    }

    private UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
