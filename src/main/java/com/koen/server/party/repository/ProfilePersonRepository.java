package com.koen.server.party.repository;

import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.ProfilePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePersonRepository extends JpaRepository<ProfilePerson,Long> {
}
