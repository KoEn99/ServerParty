package com.koen.server.party.controller;

import com.koen.server.party.dto.AuthenticationRequestDto;
import com.koen.server.party.dto.RefreshTokenRequestDto;
import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.ProfilePerson;
import com.koen.server.party.repository.AuthPersonRepository;
import com.koen.server.party.security.AuthSecurity;
import com.koen.server.party.security.jwt.JwtTokenProvider;
import com.koen.server.party.service.AuthService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class SiteController {
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(@PathVariable String code){
        AuthPerson authPerson = authPersonRepository.findByStatus(code);
        if (authPerson != null){
            authPerson.setStatus("ACTIVE");
            authService.save(authPerson);
        }
        return "activate";
    }
}
