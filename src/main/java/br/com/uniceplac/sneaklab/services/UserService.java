package br.com.uniceplac.sneaklab.services;

import br.com.uniceplac.sneaklab.dto.UserRequest;
import br.com.uniceplac.sneaklab.dto.UserResponse;
import br.com.uniceplac.sneaklab.exceptions.UserNotFoundException;
import br.com.uniceplac.sneaklab.models.User;
import br.com.uniceplac.sneaklab.repositories.UserRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public Page<UserResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public UserResponse get(Long id) {
        var u = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return toResponse(u);
    }

    public UserResponse create(UserRequest req) {
        if (repo.existsByEmailIgnoreCase(req.email())) {
            throw new DataIntegrityViolationException("Email já cadastrado");
        }
        var u = new User();
        u.setName(req.name());
        u.setEmail(req.email().trim().toLowerCase());
        u.setPasswordHash(hash(req.password()));
        var saved = repo.save(u);
        return toResponse(saved);
    }

    public UserResponse update(Long id, UserRequest req) {
        var u = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (!u.getEmail().equalsIgnoreCase(req.email()) && repo.existsByEmailIgnoreCase(req.email())) {
            throw new DataIntegrityViolationException("Email já cadastrado");
        }
        u.setName(req.name());
        u.setEmail(req.email().trim().toLowerCase());
        u.setPasswordHash(hash(req.password()));
        var saved = repo.save(u);
        return toResponse(saved);
    }

    public void delete(Long id) {
        var u = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        repo.delete(u);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getCreatedAt(), u.getUpdatedAt());
    }

    private String hash(String raw) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            var dig = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(dig);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar hash", e);
        }
    }
}
