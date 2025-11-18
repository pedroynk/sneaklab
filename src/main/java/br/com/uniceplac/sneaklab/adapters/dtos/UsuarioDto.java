package br.com.uniceplac.sneaklab.adapters.dtos;
import lombok.*;
import java.time.Instant;

public class UsuarioDto {
    private long id;

    private String name;

    private String email;

    private String passwordHash;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();
}
