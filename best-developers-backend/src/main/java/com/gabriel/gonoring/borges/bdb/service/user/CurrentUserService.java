package com.gabriel.gonoring.borges.bdb.service.user;

import com.gabriel.gonoring.borges.bdb.security.model.ApplicationUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {

    public ApplicationUserDetails getCurrentUser(){
        return (ApplicationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UUID getCurrentUserId(){
        return getCurrentUser().getId();
    }

}
