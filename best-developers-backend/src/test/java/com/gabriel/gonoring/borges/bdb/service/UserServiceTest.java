package com.gabriel.gonoring.borges.bdb.service;

import com.gabriel.gonoring.borges.bdb.dto.user.NewUserDTO;
import com.gabriel.gonoring.borges.bdb.dto.user.UpdatedUserDTO;
import com.gabriel.gonoring.borges.bdb.dto.user.UserDTO;
import com.gabriel.gonoring.borges.bdb.po.UserPO;
import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserPORepository userPORepository;

    @Captor
    private ArgumentCaptor<UserPO> userPOCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @BeforeEach
    void setUp() {
        userService = new UserService(userPORepository, new ModelMapper(), new BCryptPasswordEncoder());
    }

    private NewUserDTO createANewUserDTO(){
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setName("Gabriel gonoring borges");
        newUserDTO.setEmail("gabriel@gabriel.com");
        newUserDTO.setPassword("test123");

        return newUserDTO;
    }

    @Test
    void shouldSaveNewUser() {

        NewUserDTO newUserDTO = createANewUserDTO();

        userService.saveUser(newUserDTO);

        Mockito.verify(userPORepository, Mockito.times(1)).save(userPOCaptor.capture());
        UserPO userPO = userPOCaptor.getValue();

        Assertions.assertEquals(newUserDTO.getName(), userPO.getName());
        Assertions.assertEquals(newUserDTO.getEmail(), userPO.getEmail());
        Assertions.assertNotEquals(newUserDTO.getPassword(), userPO.getPassword());
        Assertions.assertNotNull(userPO.getPassword());
        Assertions.assertNotNull(userPO.getCreationData());

        Assertions.assertTrue(passwordEncoder.matches(newUserDTO.getPassword(), userPO.getPassword()));

    }

    @Test
    void shouldThrowsAnExceptionWhenSaveANewUser() {

        NewUserDTO newUserDTO = createANewUserDTO();

        Mockito.when(userPORepository.save(Mockito.any())).thenThrow(PersistenceException.class);

        Assertions.assertThrows(PersistenceException.class, () -> userService.saveUser(newUserDTO));
    }


    private UpdatedUserDTO createAnUpdatedUserDTO(){
        UpdatedUserDTO updatedUserDTO = new UpdatedUserDTO();
        updatedUserDTO.setId(UUID.randomUUID());
        updatedUserDTO.setName("Gabriel Gonoring Borges");
        updatedUserDTO.setEmail("gabriel@gabriel.com");
        updatedUserDTO.setPassword("test-passwd-123");

        return updatedUserDTO;
    }

    @Test
    void shouldUpdateAnUser() {
        UpdatedUserDTO updatedUserDTO = createAnUpdatedUserDTO();

        UserPO userPO = createAnUserPO();
        userPO.setId(updatedUserDTO.getId());

        Mockito.when(userPORepository.getById(updatedUserDTO.getId())).thenReturn(userPO);

        userService.updateUser(updatedUserDTO);

        Mockito.verify(userPORepository, Mockito.times(1)).save(userPOCaptor.capture());
        UserPO userPOCaptored = userPOCaptor.getValue();

        Assertions.assertEquals(updatedUserDTO.getId(), userPOCaptored.getId());
        Assertions.assertEquals(updatedUserDTO.getName(), userPOCaptored.getName());
        Assertions.assertEquals(updatedUserDTO.getEmail(), userPOCaptored.getEmail());
        Assertions.assertNotEquals(updatedUserDTO.getPassword(), userPOCaptored.getPassword());
        Assertions.assertNotNull(userPO.getPassword());

        Assertions.assertTrue(passwordEncoder.matches(updatedUserDTO.getPassword(), userPOCaptored.getPassword()));
    }



    @Test
    void shouldThrowsAnExceptionWhenUpdateAnUser() {

        UpdatedUserDTO updatedUserDTO = createAnUpdatedUserDTO();

        Mockito.when(userPORepository.getById(updatedUserDTO.getId())).thenReturn(createAnUserPO());

        Mockito.when(userPORepository.save(Mockito.any())).thenThrow(PersistenceException.class);

        Assertions.assertThrows(PersistenceException.class, () -> userService.updateUser(updatedUserDTO));
    }

    @Test
    void shouldDeleteAnUser() {
        UUID id = UUID.randomUUID();

        userService.deleteUserById(id);

        Mockito.verify(userPORepository, Mockito.times(1)).deleteById(uuidCaptor.capture());

        Assertions.assertEquals(uuidCaptor.getValue(), id);
    }

    @Test
    void shouldThrowsAnExceptionWhenDeleteAnUser() {

        Mockito.doThrow(PersistenceException.class).when(userPORepository).deleteById(Mockito.any());

        Assertions.assertThrows(PersistenceException.class, () -> userService.deleteUserById(UUID.randomUUID()));
    }


    private UserPO createAnUserPO(){
        UserPO userPO = new UserPO();
        userPO.setId(UUID.randomUUID());
        userPO.setName("Irineu da silva silvino");
        userPO.setEmail("irineu@irineu.com");
        userPO.setCreationData(LocalDateTime.now());
        userPO.setPassword("231492934p329ur23j32h4j234234");

        return userPO;
    }

    @Test
    void shouldReturnAnUser(){
        UserPO userPO = createAnUserPO();

        UUID id = UUID.randomUUID();

        Mockito.when(userPORepository.getById(id)).thenReturn(userPO);

        UserDTO userDTO = userService.getUserById(id);

        Assertions.assertEquals(userPO.getId(), userDTO.getId());
        Assertions.assertEquals(userPO.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(userPO.getName(), userDTO.getName());
        Assertions.assertEquals(userPO.getCreationData(), userDTO.getCreationData());
    }
}
