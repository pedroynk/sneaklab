package br.com.uniceplac.sneaklab.adapters.dtos;

import br.com.uniceplac.sneaklab.domain.User;
import br.com.uniceplac.sneaklab.domain.UserRole;

import java.time.Instant;

public class UsuarioDto {

    private Long id;
    private String name;
    private String email;
    private String passwordHash;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;

    public UsuarioDto() {
    }

    public UsuarioDto(Long id,
                      String name,
                      String email,
                      String passwordHash,
                      UserRole role,
                      Instant createdAt,
                      Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // mapeamento Domain <-> DTO

    public static UsuarioDto fromDomain(User user) {
        if (user == null) return null;

        return new UsuarioDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toDomain() {
        return new User(
                this.id,
                this.name,
                this.email,
                this.passwordHash,
                this.role,
                this.createdAt,
                this.updatedAt
        );
    }

    // Getters / Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
