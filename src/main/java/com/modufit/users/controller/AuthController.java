package com.modufit.users.controller;

import com.modufit.common.security.JwtService;
import com.modufit.users.dto.JwtLogin;
import com.modufit.users.dto.LoginRequest;
import com.modufit.users.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtLogin login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }
}