package br.com.realtech.ancora.controllers;

import br.com.realtech.ancora.dtos.auth.request.LoginRequest;
import br.com.realtech.ancora.dtos.auth.response.LoginResponse;
import br.com.realtech.ancora.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse login = authService.authenticateUser(loginRequest);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
