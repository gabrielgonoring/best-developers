package com.gabriel.gonoring.borges.bdb.security.service;

import com.gabriel.gonoring.borges.bdb.po.UserPO;
import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.security.model.ApplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {

    private UserPORepository userPORepository;

    @Autowired
    public UserDetailsServiceImplements(UserPORepository userPORepository) {
        this.userPORepository = userPORepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserPO> userPOOptional = userPORepository.findByEmail(email);

        if (userPOOptional.isEmpty())
            throw new UsernameNotFoundException("User does not exist");

        return new ApplicationUserDetails(userPOOptional.get().getId(), userPOOptional.get().getEmail(), userPOOptional.get().getPassword());
    }
}
