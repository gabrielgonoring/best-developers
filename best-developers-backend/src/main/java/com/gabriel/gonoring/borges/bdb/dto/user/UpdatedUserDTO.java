package com.gabriel.gonoring.borges.bdb.dto.user;

import com.gabriel.gonoring.borges.bdb.validation.annotation.EmailDoesNotExists;
import com.gabriel.gonoring.borges.bdb.validation.annotation.IsCurrentUserId;
import com.gabriel.gonoring.borges.bdb.validation.annotation.UpdatedEmailDoesNotExist;
import com.gabriel.gonoring.borges.bdb.validation.group.SecundaryValidation;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@GroupSequence({UpdatedUserDTO.class, SecundaryValidation.class})
@UpdatedEmailDoesNotExist(groups = SecundaryValidation.class)
public class UpdatedUserDTO {

    @NotNull(message = "User id can not be null")
    @IsCurrentUserId(message = "You can not update another users. This user is not yours", groups = SecundaryValidation.class)
    private UUID id;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email can not be blank")
    @Email(message = "This email is not valid")
    private String email;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name can not be blank")
    private String name;

    private String password;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
