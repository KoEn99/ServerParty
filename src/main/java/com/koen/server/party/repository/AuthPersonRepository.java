package com.koen.server.party.repository;

import com.koen.server.party.entity.AuthPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthPersonRepository extends JpaRepository<AuthPerson, Long> {
    AuthPerson findByEmail(String email);
    AuthPerson findAuthPersonByEmail(String email);
    AuthPerson findByStatus(String status);
}
