package com.koen.server.party.security;

import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.repository.AuthPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AuthSecurity  {
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
 /*   @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        AuthPerson authPerson = authPersonRepository.findByEmail(email);
        if (authPerson == null){
            throw new UsernameNotFoundException("Don't found user");
        }
        String password = (String) authentication.getCredentials();
        if (!passwordEncoder.matches(password, authPerson.getPassword())){
            throw new BadCredentialsException("Пароли не совпадают");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(authPerson, null, authorityList);
    }
*/

}
