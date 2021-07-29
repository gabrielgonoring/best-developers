package com.gabriel.gonoring.borges.bdb.security.service;

import com.gabriel.gonoring.borges.bdb.security.model.AuthenticationData;
import com.gabriel.gonoring.borges.bdb.security.model.AuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JWTTokenService jwtTokenService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JWTTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthorizationToken authenticate(AuthenticationData authenticationData){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationData.getEmail(), authenticationData.getPassword()));

        return jwtTokenService.createAuthorizationToken(authentication);

    }
}
