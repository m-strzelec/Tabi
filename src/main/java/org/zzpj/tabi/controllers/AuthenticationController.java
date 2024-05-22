package org.zzpj.tabi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.dto.AccountDTOs.ChangeSelfPasswordDTO;
import org.zzpj.tabi.dto.LoginDTO;
import org.zzpj.tabi.dto.RegisterAccountDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.mappers.AccountMapper;
import org.zzpj.tabi.security.jws.JwsService;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwsService jwsService;

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
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("Invalid request - Account is locked");
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

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successful logout")
    })
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/self")
    @Operation(summary = "Get information about own account", description = "Get information about yourself using security context holder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get own account successfully"),
            @ApiResponse(responseCode = "404", description = "Client with specified account doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Other problems occurred eg. database connection error")
    })
    public ResponseEntity<?> getSelf() {
        try {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Account account = accountService.getAccountByLogin(login);
            String etagValue = jwsService.signAccount(account);
            AccountDTO accountDTO = AccountMapper.toAccountDTO(account);
            HttpHeaders headers = new HttpHeaders();
            headers.setETag("\"" + etagValue + "\"");
            return ResponseEntity.ok().headers(headers).body(accountDTO);
        } catch(AccountNotFoundException anfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified name doesn't exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Could not find account");
        }
    }

    @PutMapping("/self")
    @Operation(summary = "Modify own account", description = "Modify own account using security context holder", parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "If-Match", description = "ETag for conditional requests")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account modified successfully"),
            @ApiResponse(responseCode = "400", description = "If-mach header invalid"),
            @ApiResponse(responseCode = "404", description = "Account doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Other problems occurred eg. database connection error")
    })
    public ResponseEntity<?> updateSelf(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        try {
            if (ifMatch == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-mach header is required");
            }
            if (!jwsService.isIfMatchValid(ifMatch, accountUpdateDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-mach header is invalid");
            }
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            accountService.modifyAccount(accountUpdateDTO, login);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException anfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified uuid doesn't exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Could not modify account");
        }
    }
}
