package com.gabriel.gonoring.borges.bdb.security.service;

import com.gabriel.gonoring.borges.bdb.po.UserPO;
import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.security.model.ApplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationService {

    private UserPORepository userPORepository;

    @Autowired
    public AuthorizationService(UserPORepository userPORepository) {
        this.userPORepository = userPORepository;
    }

    public void setAuthenticatedUser(UUID userID){
        UserPO userPO = userPORepository.findById(userID).orElseThrow();

        ApplicationUserDetails applicationUserDetails = new ApplicationUserDetails(userPO.getId(), userPO.getEmail(), userPO.getPassword());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(applicationUserDetails, null, applicationUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
