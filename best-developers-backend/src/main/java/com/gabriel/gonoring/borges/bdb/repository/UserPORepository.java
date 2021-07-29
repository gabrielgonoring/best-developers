package com.gabriel.gonoring.borges.bdb.repository;

import com.gabriel.gonoring.borges.bdb.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPORepository extends JpaRepository<UserPO, UUID> {

    Optional<UserPO> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndEmail(UUID id, String email);
}
