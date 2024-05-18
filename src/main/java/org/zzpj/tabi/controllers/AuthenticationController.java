package org.zzpj.tabi.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.LoginDTO;
import org.zzpj.tabi.dto.LoginFormDTO;
import org.zzpj.tabi.dto.RegisterAccountDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Roles;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.security.JwtService;
import org.zzpj.tabi.services.AccountService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginFormDTO credentials) {
        try {
            String token = accountService.addUser2(credentials, Roles.CLIENT);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account with given email already exists", exception);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getName(), credentials.getPassword())
        );
        var user = accountService.getClientByLogin(credentials.getName());
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @GetMapping("/covered")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok("covered");
    }
}
