package com.koen.server.party.service;

import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.ProfilePerson;
import com.koen.server.party.repository.AuthPersonRepository;
import com.koen.server.party.repository.ProfilePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Service
public class AuthService implements ServiceController {
    @Autowired
    private AuthPersonRepository authPersonRepository;
    @Autowired
    private ProfilePersonRepository profilePersonRepository;
    @PersistenceContext
    private EntityManager em;

    public void save(Object object) {
        AuthPerson authPerson = (AuthPerson) object;
        ProfilePerson profilePerson = authPerson.getProfilePerson();
        if (authPerson.getStatus() != null) {
            if (!authPerson.getStatus().equals("ACTIVE")) {
                String uuid = UUID.randomUUID().toString();
                authPerson.setStatus(uuid);
            }
        } else{
            String uuid = UUID.randomUUID().toString();
            authPerson.setStatus(uuid);
        }
        profilePerson.setAuthPerson(authPerson);
        authPersonRepository.save(authPerson);
    }

    public void remove(Object object) {

    }
}
