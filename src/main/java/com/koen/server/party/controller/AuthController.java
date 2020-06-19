package com.koen.server.party.controller;

import com.koen.server.party.dto.*;
import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.repository.AuthPersonRepository;
import com.koen.server.party.security.jwt.JwtTokenProvider;
import com.koen.server.party.security.jwt.JwtUser;
import com.koen.server.party.service.AuthService;
import com.koen.server.party.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController{
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private HashMap<String, String> refreshTokensStorage = new HashMap<String, String>();
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    private AuthService authService;
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSuccsess(){
        System.out.println("GGGG");
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/profile-person", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        return ResponseEntity.ok(new ProfilePersonDto(
                authPerson.getEmail(),
                authPerson.getProfilePerson().getName(),
                authPerson.getProfilePerson().getMiddleName(),
                authPerson.getProfilePerson().getNumberPhone(),
                authPerson.getProfilePerson().getAvatar(),
                authPerson.getProfilePerson().getSurname(),
                authPerson.getProfilePerson().getCity()
        ));
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity addAccount(@RequestBody AuthPerson authPerson){
        if (authPersonRepository.findByEmail(authPerson.getEmail()) == null) {
            authPerson.setPassword(passwordEncoder.encode(authPerson.getPassword()));
            authService.save(authPerson);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }
    @RequestMapping(value = "/new-password", method = RequestMethod.POST)
    public ResponseEntity setPassword(@RequestBody PasswordDto passwordDto){
        String oldPassword = passwordDto.getOldPassword();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        if (passwordEncoder.matches(oldPassword, authPerson.getPassword())) {
            authPerson.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
            authService.save(authPerson);
            return new ResponseEntity(HttpStatus.CREATED);
        }else return new ResponseEntity(HttpStatus.CONFLICT);
    }
    @RequestMapping(value = "/refresher", method = RequestMethod.POST)
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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
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
            return ResponseEntity.ok(getResponse(email));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
    @Autowired
    MailSender mailSender;
    @PostMapping(value = "/send-email")
    public ResponseEntity sendEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        if (!authPerson.getStatus().equals("ACTIVE")) {
            mailSender.send(authPerson.getEmail(), "Активация аккаунта",
                    "Для подтверждения аккаунта пройдите по ссылке: http://178.170.220.39:8080/activate/" +
                            authPerson.getStatus());
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.CONFLICT);
    }
    @PostMapping(value = "/reset-password")
    public ResponseEntity resetPassword(@RequestBody EmailDto emailDto) {
        AuthPerson authPerson = authPersonRepository.findByEmail(emailDto.getEmail());
        String password = UUID.randomUUID().toString();
        password = password.substring(0,8);
        authPerson.setPassword(passwordEncoder.encode(password));
        authService.save(authPerson);
        if (authPerson.getStatus().equals("ACTIVE")) {
            mailSender.send(authPerson.getEmail(), "Восстановление пароля",
                    "Никому не сообщайте пароль. Ваш новый пароль: " +
                            password);
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.CONFLICT);
    }
    @RequestMapping(value = "/fio", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getFio(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        FioDto fioDto = new FioDto();
        fioDto.setFIO(authPerson.getProfilePerson().getName() + " " + authPerson.getProfilePerson().getMiddleName());
        return ResponseEntity.ok(fioDto);
    }
  @Value("${upload.path}")
  String uploadpath;
  @RequestMapping(value = "/image/load", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity setImage(@RequestParam("file") MultipartFile file) throws IOException {
      String filename = "";
      if (file != null){
          File uploaddir = new File(uploadpath);
          if (!uploaddir.exists()){
              uploaddir.mkdir();
          }
          String uuifile = UUID.randomUUID().toString();
          filename = uuifile + "." + file.getOriginalFilename();
          //  file.transferTo(new File(uploadpath+ "/" +filename));
          byte[] bytes = file.getBytes();
          BufferedOutputStream stream =
                  new BufferedOutputStream(new FileOutputStream(new File(uploadpath + "/" + filename)));
          stream.write(bytes);
          stream.close();
      }
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      JwtUser userDetails = (JwtUser) authentication.getPrincipal();
      AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
      authPerson.getProfilePerson().setAvatar(filename);
      authService.save(authPerson);
      return new ResponseEntity(HttpStatus.OK);
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
