package com.dga.contactbook.controller;

import com.dga.contactbook.dto.AuthenticateRequest;
import com.dga.contactbook.dto.AuthenticationResponse;
import com.dga.contactbook.dto.RegisterRequest;
import com.dga.contactbook.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.signup(registerRequest));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> signin(@Valid @RequestBody AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok(authenticationService.signin(authenticateRequest));
    }


}
