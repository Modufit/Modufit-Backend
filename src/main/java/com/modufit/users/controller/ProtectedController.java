package com.modufit.users.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        // authentication은 JwtAuthFilter가 SecurityContext에 넣어준 값
        String username = authentication.getName();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new MeResponse(username, roles);
    }

    public record MeResponse(String username, List<String> roles) {}
}
