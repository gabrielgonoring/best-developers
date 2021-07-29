package com.gabriel.gonoring.borges.bdb.controller;

import com.gabriel.gonoring.borges.bdb.dto.user.NewUserDTO;
import com.gabriel.gonoring.borges.bdb.dto.user.UpdatedUserDTO;
import com.gabriel.gonoring.borges.bdb.dto.user.UserDTO;
import com.gabriel.gonoring.borges.bdb.service.user.UserService;
import com.gabriel.gonoring.borges.bdb.validation.annotation.IsCurrentUserId;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation("Return an user by user id")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("userId") @NotNull @IsCurrentUserId(message = "You can only see informations about your user") UUID userId){

        LOGGER.info("Searching for user with id {}", userId);

        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @ApiOperation("Delete an user by user id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable("userId") @NotNull @IsCurrentUserId(message = "You can only delete your account. This user is not yours") UUID userId){

        LOGGER.info("Deleting an user with ID {}", userId);

        userService.deleteUserById(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation("Create a new user")
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid NewUserDTO newUserDTO){

        LOGGER.info("Saving a new user with email '{}' and name '{}'", newUserDTO.getEmail(), newUserDTO.getName()); //todo: show user information

        userService.saveUser(newUserDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Update an existing user")
    @PutMapping
    //@CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdatedUserDTO userDTO){

        LOGGER.info("Updating a user with id {}", userDTO.getId()); //todo: show user information

        userService.updateUser(userDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
