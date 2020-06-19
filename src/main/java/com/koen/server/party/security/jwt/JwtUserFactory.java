package com.koen.server.party.security.jwt;

import com.koen.server.party.entity.AuthPerson;

public class JwtUserFactory {
    public JwtUserFactory() {
    }
    public static JwtUser create(AuthPerson authPerson){
        return new JwtUser(
                authPerson.getPassword(),
                authPerson.getEmail()
        );
    }
}
