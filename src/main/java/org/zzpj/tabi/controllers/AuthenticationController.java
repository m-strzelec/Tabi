package org.zzpj.tabi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.zzpj.tabi.dto.AccountDTOs.ChangeSelfPasswordDTO;
import org.zzpj.tabi.dto.LoginDTO;
import org.zzpj.tabi.dto.RegisterAccountDTO;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    @Operation(summary = "Register client", description = "Register new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "New client successfully created"),
            @ApiResponse(responseCode = "409", description = "User with given data exist"),
            @ApiResponse(responseCode = "500", description = "New client couldn't be created"),
    })
    public ResponseEntity<?> register(@RequestBody RegisterAccountDTO clientData) {
        try {
            accountService.registerClient(clientData);
            return ResponseEntity.noContent().build();
        }
        catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account with given email already exist");
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - New client couldn't be created");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login into system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successful login"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Could not login successfully")
    })
    public ResponseEntity<?> login(@RequestBody LoginDTO credentials) {
        try {
            String token = accountService.login(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException aex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Could not login successfully");
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangeSelfPasswordDTO dto) {
        try {
            accountService.changePassword(dto);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }

    }
}
