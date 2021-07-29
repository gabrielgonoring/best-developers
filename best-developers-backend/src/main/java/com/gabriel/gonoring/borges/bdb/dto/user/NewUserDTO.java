package com.gabriel.gonoring.borges.bdb.dto.user;


import com.gabriel.gonoring.borges.bdb.validation.annotation.EmailDoesNotExists;
import com.gabriel.gonoring.borges.bdb.validation.group.SecundaryValidation;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@GroupSequence({NewUserDTO.class, SecundaryValidation.class})
public class NewUserDTO {

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email can not be blank")
    @Email(message = "This email is not valid")
    @EmailDoesNotExists(groups = SecundaryValidation.class)
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password can not be blank")
    private String password;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name can not be blank")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
