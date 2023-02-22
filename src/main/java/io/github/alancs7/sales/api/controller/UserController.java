package io.github.alancs7.sales.api.controller;

import io.github.alancs7.sales.api.dto.CredentialsDTO;
import io.github.alancs7.sales.api.dto.TokenDTO;
import io.github.alancs7.sales.domain.entity.User;
import io.github.alancs7.sales.service.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO save(@RequestBody @Valid User user) {
        return userDetailsService.save(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody CredentialsDTO credentials) {
        return ResponseEntity.ok(userDetailsService.authenticate(credentials));
    }
}
