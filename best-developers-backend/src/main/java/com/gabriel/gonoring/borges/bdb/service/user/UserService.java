package com.gabriel.gonoring.borges.bdb.service.user;

import com.gabriel.gonoring.borges.bdb.dto.user.NewUserDTO;
import com.gabriel.gonoring.borges.bdb.dto.user.UpdatedUserDTO;
import com.gabriel.gonoring.borges.bdb.dto.user.UserDTO;
import com.gabriel.gonoring.borges.bdb.po.UserPO;
import com.gabriel.gonoring.borges.bdb.repository.UserPORepository;
import com.gabriel.gonoring.borges.bdb.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserPORepository userPORepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserPORepository userPORepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userPORepository = userPORepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void saveUser(NewUserDTO newUserDTO){
        UserPO userPO = modelMapper.map(newUserDTO, UserPO.class);
        userPO.setCreationData(LocalDateTime.now());
        userPO.setPassword(encodePassword(newUserDTO.getPassword()));

        userPORepository.save(userPO);
    }

    public UserDTO getUserById(UUID userId){
        UserPO userPO = userPORepository.getById(userId);
        return modelMapper.map(userPO, UserDTO.class);
    }

    @Transactional
    public void updateUser(UpdatedUserDTO updatedUserDTO){
        UserPO userPO = userPORepository.getById(updatedUserDTO.getId());

        setUpdatedFieldsInUserPO(userPO, updatedUserDTO); //todo: try to refactor it

        userPORepository.save(userPO);
    }

    public void setUpdatedFieldsInUserPO(UserPO userPO, UpdatedUserDTO updatedUserDTO){
        userPO.setEmail(Optional.ofNullable(updatedUserDTO.getEmail()).orElse(userPO.getEmail()));
        userPO.setName(Optional.ofNullable(updatedUserDTO.getName()).orElse(userPO.getName()));

        if (Utils.isStringNotNullAndNotBlack(updatedUserDTO.getPassword()))
            userPO.setPassword(encodePassword(updatedUserDTO.getPassword()));
    }

    @Transactional
    public void deleteUserById(UUID userId){
        userPORepository.deleteById(userId);
    }


    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }


}
