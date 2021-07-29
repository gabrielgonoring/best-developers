package com.gabriel.gonoring.borges.bdb.security.service;

import com.gabriel.gonoring.borges.bdb.po.UserPO;
import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.security.model.ApplicationUserDetails;
import com.gabriel.gonoring.borges.bdb.security.service.UserDetailsServiceImplements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplementsTest {

    private UserDetailsServiceImplements userDetailsServiceImplements;

    @Mock
    private UserPORepository userPORepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        userDetailsServiceImplements = new UserDetailsServiceImplements(userPORepository);
    }

    private UserPO createUserPO(){
        UserPO userPO = new UserPO();
        userPO.setId(UUID.randomUUID());
        userPO.setName("Gabriel Gonoring Borges");
        userPO.setEmail("gabriel@gabriel.com");
        userPO.setPassword(passwordEncoder.encode("password"));
        userPO.setCreationData(LocalDateTime.now());

        return userPO;
    }

    @Test
    void shouldReturnAnUser() {
        UserPO userPO = createUserPO();

        Mockito.when(userPORepository.findByEmail(userPO.getEmail())).thenReturn(Optional.ofNullable(userPO));

        UserDetails userDetails = userDetailsServiceImplements.loadUserByUsername(userPO.getEmail());
        Assertions.assertTrue(userDetails instanceof ApplicationUserDetails);

        ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) userDetails;

        Assertions.assertEquals(userPO.getId(), applicationUserDetails.getId());
        Assertions.assertEquals(userPO.getEmail(), applicationUserDetails.getUsername());
        Assertions.assertEquals(userPO.getPassword(), applicationUserDetails.getPassword());
        Assertions.assertNotNull(applicationUserDetails.getAuthorities());

    }

    @Test
    void shouldThrowsAnException() {
        String email = "test@test.com";

        Mockito.when(userPORepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImplements.loadUserByUsername(email));
    }
}
