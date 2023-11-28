package com.dga.contactbook.service;

import com.dga.contactbook.dto.AuthenticateRequest;
import com.dga.contactbook.dto.AuthenticationResponse;
import com.dga.contactbook.dto.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse signup(RegisterRequest registerRequest);

    AuthenticationResponse signin(AuthenticateRequest authenticateRequest);

}
