package com.koen.server.party.service;

import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.ProfilePerson;

public interface ServiceController {
    void save(Object object);
    void remove(Object object);
}
