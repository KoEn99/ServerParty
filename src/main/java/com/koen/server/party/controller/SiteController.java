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
    private HashMap<String, String> refreshTokensStorage = new HashMap<String, String>();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthSecurity authSecurity;

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(@PathVariable String code){
        AuthPerson authPerson = authPersonRepository.findByStatus(code);
        if (authPerson != null){
            authPerson.setStatus("ACTIVE");
            authService.save(authPerson);
        }
        return "activate";
    }



    @RequestMapping(value = "/", method = RequestMethod.GET )
    public String mainActivity(){
        return "activate";
    }
    @RequestMapping(value = "/answer", method = RequestMethod.GET )
    public String answer(){
        return "COOOOOOOLLLLL";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET )
    public String addAtribut(Model model ) {
        model.addAttribute("userForm", new AuthPerson());
        model.addAttribute("userForm1", new ProfilePerson());
        return "RegisterActivity";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST )
    public String AddPerson(@ModelAttribute("userForm") @Valid AuthPerson authPerson,@ModelAttribute("userForm1") @Valid ProfilePerson profilePerson,
                            BindingResult bindingResult, Model model) {
        authService.save(authPerson);
        return "login";
    }
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model, @AuthenticationPrincipal AuthPerson authPerson){
       // ProfilePerson profilePerson = authService.getById();
     //   model.addAttribute("name", profilePerson.getName());
    //    model.addAttribute("middlename", profilePerson.getMiddleName());
      //  model.addAttribute("numberphone", profilePerson.getNumberPhone());
        return "profilePerson";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }
    @RequestMapping(value = "/delAccount", method = RequestMethod.GET)
    public String getProfile(@AuthenticationPrincipal AuthPerson authPerson){
//        if (authService.remove(authPerson)) return "login";
        return "profilePerson";
    }
    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity  getNewToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        if (refreshTokensStorage.get(refreshTokenRequestDto.refreshToken) == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (refreshTokensStorage.
                get(refreshTokenRequestDto.refreshToken).
                equals(refreshTokenRequestDto.email)) {
            refreshTokensStorage.remove(refreshTokenRequestDto.refreshToken);
            return ResponseEntity.ok(getResponse(refreshTokenRequestDto.email));
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.email;
            AuthPerson authPerson = authPersonRepository.findByEmail(email);
            if (authPerson == null){
                throw new UsernameNotFoundException("Don't found user");
            }
            String password = requestDto.password;
            if (!passwordEncoder.matches(password, authPerson.getPassword())){
                throw new BadCredentialsException("Пароли не совпадают");
            }
            List<GrantedAuthority> authorityList = new ArrayList<>();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(requestDto, null, Collections.emptyList());
           // authenticationManager.authenticate(usernamePasswordAuthenticationToken);
           SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return ResponseEntity.ok(getResponse(email));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
    private Map<Object, Object> getResponse(String email){
        String token = jwtTokenProvider.createToken(email);
        long number = 259200000 * 10;
        String token_refresh = jwtTokenProvider.createToken(email, number);
        Map<Object, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("token", token);
        response.put("token_refresh", token_refresh);
        refreshTokensStorage.put(token_refresh, email);
        return response;
    }
}
