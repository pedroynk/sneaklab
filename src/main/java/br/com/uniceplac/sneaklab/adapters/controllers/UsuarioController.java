package br.com.uniceplac.sneaklab.adapters.controllers;

import br.com.uniceplac.sneaklab.adapters.dtos.UsuarioDto;
import br.com.uniceplac.sneaklab.application.ports.in.GerenciarUsuarioUseCase;
import br.com.uniceplac.sneaklab.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final GerenciarUsuarioUseCase gerenciarUsuarioUseCase;

    public UsuarioController(GerenciarUsuarioUseCase gerenciarUsuarioUseCase) {
        this.gerenciarUsuarioUseCase = gerenciarUsuarioUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto criar(@RequestBody UsuarioDto dto) {
        User criado = gerenciarUsuarioUseCase.criar(dto.toDomain());
        return UsuarioDto.fromDomain(criado);
    }

    @GetMapping
    public List<UsuarioDto> listar() {
        return gerenciarUsuarioUseCase.listar()
                .stream()
                .map(UsuarioDto::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsuarioDto buscarPorId(@PathVariable Long id) {
        User user = gerenciarUsuarioUseCase.buscarPorId(id);
        return UsuarioDto.fromDomain(user);
    }

    @PutMapping("/{id}")
    public UsuarioDto atualizar(@PathVariable Long id, @RequestBody UsuarioDto dto) {
        User atualizado = gerenciarUsuarioUseCase.atualizar(id, dto.toDomain());
        return UsuarioDto.fromDomain(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        gerenciarUsuarioUseCase.deletar(id);
    }
}
