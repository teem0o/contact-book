package com.dga.contactbook.service.impl;

import com.dga.contactbook.dto.AuthenticateRequest;
import com.dga.contactbook.dto.AuthenticationResponse;
import com.dga.contactbook.dto.RegisterRequest;
import com.dga.contactbook.entity.User;
import com.dga.contactbook.exception.UsernameAlreadyExistsException;
import com.dga.contactbook.repository.UserRepository;
import com.dga.contactbook.security.JWTService;
import com.dga.contactbook.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthenticationResponse signup(RegisterRequest registerRequest) throws RuntimeException {
        var user = new User();
        user.setUsername(registerRequest.getUsername());
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse signin(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),
                        authenticateRequest.getPassword()));
        var user = userRepository.findByUsername(authenticateRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
