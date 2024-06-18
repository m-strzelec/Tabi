package org.zzpj.tabi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zzpj.tabi.dto.account.LoginDTO;
import org.zzpj.tabi.dto.account.RegisterAccountDTO;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    @Operation(summary = "Register client", description = "Register new client")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "New client successfully created",
                    content = {@Content(mediaType = "text/plain",
                                examples = @ExampleObject("201 Created"))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with given data exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("409 Conflict"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "New client could not be created for unknown reason",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            ),
    })
    public ResponseEntity<?> register(@RequestBody RegisterAccountDTO clientData) {
        try {
            accountService.registerClient(clientData);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
        }
        catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account with given email already exist");
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: New client could not be created");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login into system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successful login",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGljZSIsImlhdCI6MTcxNjM4NDk2MCwiZXhwIjoxNzE2Mzg2NDAwfQ.HY7_c79eMHS2lk9PGNTr5eDyksNueahSlkPA6Hlx9uABgHnBiFGJvVxGcBoDMZg44t9boDSW1rKtIiqAO8Hhww")
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid credentials",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "423",
                    description = "Account is locked",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("423 Locked"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Could not login successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> login(@RequestBody LoginDTO credentials) {
        try {
            String token = accountService.login(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("Invalid request: Account is locked");
        } catch (AuthenticationException aex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not login successfully");
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout from system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User successful logout",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("204 No Content"))}
            )
    })
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
