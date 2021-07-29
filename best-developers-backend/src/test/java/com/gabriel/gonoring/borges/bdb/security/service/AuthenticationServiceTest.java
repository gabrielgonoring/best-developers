package com.gabriel.gonoring.borges.bdb.security.service;

import com.gabriel.gonoring.borges.bdb.security.model.AuthenticationData;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTTokenService jwtTokenService;

    @Captor
    ArgumentCaptor<Authentication> authenticationArgumentCaptor;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(authenticationManager, jwtTokenService);
    }

    private AuthenticationData authenticationData(){
        AuthenticationData authenticationData = new AuthenticationData();

        authenticationData.setEmail("gabriel@gabriel.com");
        authenticationData.setPassword("password123");

        return authenticationData;
    }

    @Test
    void shouldAuthenticateUser() {

        AuthenticationData authenticationData = authenticationData();

        when(authenticationManager.authenticate(authenticationArgumentCaptor.capture())).thenReturn(new UsernamePasswordAuthenticationToken(authenticationData.getEmail(), authenticationData.getPassword()));

        authenticationService.authenticate(authenticationData);

        verify(authenticationManager, times(1)).authenticate(any());

        verify(jwtTokenService, times(1)).createAuthorizationToken(any());

        Authentication authentication = authenticationArgumentCaptor.getValue();

        assertEquals(authenticationData.getEmail(), authentication.getPrincipal());
        assertEquals(authenticationData.getPassword(), authentication.getCredentials());
    }

    @Test
    void shouldNotAuthenticateUser() {

        AuthenticationData authenticationData = authenticationData();

        when(authenticationManager.authenticate(authenticationArgumentCaptor.capture())).thenThrow(UsernameNotFoundException.class);

        assertThrows(AuthenticationException.class, ()-> authenticationService.authenticate(authenticationData));
    }
}
