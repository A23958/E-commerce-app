package com.andy.ecommerce_project.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @GetMapping("/api/auth/csrf")
        public Map<String, String> getCsrfToken(CsrfToken csrfToken) {
            return Map.of(
                    "headerName", csrfToken.getHeaderName(),
                    "token", csrfToken.getToken()
            );
        }

    @GetMapping("/api/auth/me")
    public ResponseEntity<Void> user() {
        return ResponseEntity.noContent().build();
    }
    
}
