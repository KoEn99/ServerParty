package com.koen.server.party.security;

import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.repository.AuthPersonRepository;
import com.koen.server.party.security.jwt.JwtUser;
import com.koen.server.party.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {
    @Autowired
    AuthPersonRepository authPersonRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthPerson authPerson = authPersonRepository.findByEmail(email);
        if (authPerson == null) {
            throw new UsernameNotFoundException("User with username: " + email + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(authPerson);
        return jwtUser;
    }
}
