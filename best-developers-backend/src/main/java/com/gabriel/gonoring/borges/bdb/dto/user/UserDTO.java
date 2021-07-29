package com.gabriel.gonoring.borges.bdb.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String email;
    private String name;
    private LocalDateTime creationData;

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

    public LocalDateTime getCreationData() {
        return creationData;
    }

    public void setCreationData(LocalDateTime creationData) {
        this.creationData = creationData;
    }
}
