package com.gabriel.gonoring.borges.bdb.controller;

import com.gabriel.gonoring.borges.bdb.security.model.AuthenticationData;
import com.gabriel.gonoring.borges.bdb.security.model.AuthorizationToken;
import com.gabriel.gonoring.borges.bdb.security.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiOperation("Return a token that grant API access")
    @PostMapping
    public ResponseEntity<AuthorizationToken> authenticate(@RequestBody @Valid AuthenticationData authenticationData){

        LOGGER.info("Processing a request to authenticate an user that has the email '{}'", authenticationData.getEmail());

        return ResponseEntity.ok(authenticationService.authenticate(authenticationData));
    }

}
