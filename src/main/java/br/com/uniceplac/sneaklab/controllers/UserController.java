package br.com.uniceplac.sneaklab.controllers;

import java.util.Set;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniceplac.sneaklab.dto.UserRequest;
import br.com.uniceplac.sneaklab.dto.UserResponse;
import br.com.uniceplac.sneaklab.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Set<String> ALLOWED_SORT = Set.of("id", "name", "email", "createdAt", "updatedAt");

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Page<UserResponse> list(@ParameterObject Pageable pageable) {
        Pageable safe = sanitize(pageable);
        return service.list(safe);
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest body) {
        return service.create(body);
    }

    @PutMapping
    public UserResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private Pageable sanitize(Pageable p) {
        Sort safeSort = Sort.by(
                p.getSort().stream()
                        .filter(o -> ALLOWED_SORT.contains(o.getProperty()))
                        .toList());
        if (safeSort.isUnsorted())
            safeSort = Sort.by("id").ascending();
        int page = Math.max(p.getPageNumber(), 0);
        int size = p.getPageSize() > 0 ? p.getPageSize() : 20;
        return PageRequest.of(page, size, safeSort);
    }
}
