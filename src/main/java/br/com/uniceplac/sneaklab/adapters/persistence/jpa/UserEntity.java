package br.com.uniceplac.sneaklab.adapters.persistence.jpa;

import br.com.uniceplac.sneaklab.domain.UserRole;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // para simplificar, mesma coluna; depois você pode colocar hashing de fato
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private Instant createdAt;
    private Instant updatedAt;

    public UserEntity() {
    }

    public UserEntity(Long id,
                      String name,
                      String email,
                      String passwordHash,
                      UserRole role,
                      Instant createdAt,
                      Instant updatedAt) {
        this.id = Math.toIntExact(id);
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters e Setters

    public Long getId() {
        return (long) id;
    }

    public void setId(Long id) {
        this.id = Math.toIntExact(id);
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
