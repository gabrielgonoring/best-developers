package com.gabriel.gonoring.borges.bdb.security.service;

import com.gabriel.gonoring.borges.bdb.security.model.ApplicationUserDetails;
import com.gabriel.gonoring.borges.bdb.security.model.AuthorizationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTTokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public AuthorizationToken createAuthorizationToken(Authentication authentication){

        ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) authentication.getPrincipal();

        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + Long.parseLong(expiration));

        String token = Jwts.builder()
                            .setIssuer("API best developers")
                            .setSubject(applicationUserDetails.getId().toString())
                            .setIssuedAt(creationDate)
                            .setExpiration(expirationDate)
                            .signWith(SignatureAlgorithm.HS256, secret)
                            .compact();

        AuthorizationToken authorizationToken = new AuthorizationToken();
        authorizationToken.setUserId(applicationUserDetails.getId().toString());
        authorizationToken.setType("Bearer");
        authorizationToken.setToken(token);
        authorizationToken.setCreationDate(creationDate);
        authorizationToken.setExpirationDate(expirationDate);


        return authorizationToken;
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getTokenSubject(String token){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
