package com.gabriel.gonoring.borges.bdb.security.filter;

import com.gabriel.gonoring.borges.bdb.security.service.AuthorizationService;
import com.gabriel.gonoring.borges.bdb.security.service.JWTTokenService;
import com.gabriel.gonoring.borges.bdb.util.Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private JWTTokenService jwtTokenService;
    private AuthorizationService authorizationService;

    public JWTAuthorizationFilter(JWTTokenService jwtTokenService, AuthorizationService authorizationService) {
        this.jwtTokenService = jwtTokenService;
        this.authorizationService = authorizationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenInRequestHeader(httpServletRequest);

        if (jwtTokenService.isTokenValid(token)){
            UUID userID = UUID.fromString(jwtTokenService.getTokenSubject(token));
            authorizationService.setAuthenticatedUser(userID);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getTokenInRequestHeader(HttpServletRequest httpServletRequest){

        String token = httpServletRequest.getHeader("Authorization");

        if (Utils.isStringNullOrBlack(token) || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7);
    }
}
