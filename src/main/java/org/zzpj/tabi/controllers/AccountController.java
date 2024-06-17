package org.zzpj.tabi.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.AddCardDTO;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.dto.AccountDTOs.ChangeSelfPasswordDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.CardAddException;
import org.zzpj.tabi.exceptions.CardAlreadyExistsException;
import org.zzpj.tabi.exceptions.OldPasswordNotMatchException;
import org.zzpj.tabi.mappers.AccountMapper;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.security.jws.JwsService;
import org.zzpj.tabi.services.AccountService;
import org.zzpj.tabi.services.PaymentService;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JwsService jwsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all accounts", description = "Get all accounts from system\n\nRoles: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all accounts",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<AccountDTO> accountList = accountService
                    .getAllAccounts()
                    .stream()
                    .map(AccountMapper::toAccountDTO)
                    .toList();
            return ResponseEntity.ok().body(accountList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not find accounts");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    @Operation(summary = "Get account by UUID", description = "Get account with specified UUID\n\nRoles: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found account with specified UUID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "UUID has invalid format",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with specified UUID does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> getAccountById(@PathVariable("uuid") UUID uuid) {
        try {
            Account account = accountService.getClientById(uuid);
            String etagValue = jwsService.signAccount(account);
            AccountDTO accountDTO = AccountMapper.toAccountDTO(accountService.getClientById(uuid));
            HttpHeaders headers = new HttpHeaders();
            headers.setETag("\"" + etagValue + "\"");
            return ResponseEntity.ok().headers(headers).body(accountDTO);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with specified UUID does not exist");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not find account");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    @Operation(summary = "Edit account", description = "Edit account with specified UUID\n\nRoles: ADMIN", parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "If-Match", description = "ETag for conditional requests")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account modified successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "If-match header invalid",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> updateAccount(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        try {
            if (ifMatch == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-match header is required");
            }
            if (!jwsService.isIfMatchValid(ifMatch, accountUpdateDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-match header is invalid");
            }
            accountService.modifyAccount(accountUpdateDTO, accountUpdateDTO.getLogin());
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException anfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified UUID does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not modify account");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{uuid}/block")
    @Operation(summary = "Block account", description = "Block account with specified UUID\n\nRoles: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account blocked successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with specified UUID does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> blockAccount(@PathVariable("uuid") UUID uuid) {
        try {
            Account account = accountService.getClientById(uuid);
            account.block();
            accountRepository.save(account);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified UUID does not exist");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not block account");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{uuid}/unblock")
    @Operation(summary = "Unblock account", description = "Unblock account with specified UUID\n\nRoles: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account unblocked successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with specified UUID does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> unblockAccount(@PathVariable("uuid") UUID uuid) {
        try {
            Account account = accountService.getClientById(uuid);
            account.unblock();
            accountRepository.save(account);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified UUID does not exist");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not unblock account");
        }
    }

    @PostMapping("/change-password-self")
    @Operation(summary = "Change your password", description = "Change your password using security context holder")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Password changed successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Old password does not match or user does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems e.g. database error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> changePassword(@RequestBody ChangeSelfPasswordDTO dto) {
        try {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            accountService.changePassword(dto, login);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        } catch (OldPasswordNotMatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password does not match");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not change password");
        }
    }

    @GetMapping("/self")
    @Operation(summary = "Get information about own account", description = "Get information about yourself using security context holder")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get own account successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client with specified account does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified name does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not find account");
        }
    }

    @PutMapping("/self")
    @Operation(summary = "Modify own account", description = "Modify own account using security context holder", parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "If-Match", description = "ETag for conditional requests")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account modified successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "If-match header invalid",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> updateSelf(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        try {
            if (ifMatch == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-match header is required");
            }
            if (!jwsService.isIfMatchValid(ifMatch, accountUpdateDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-match header is invalid");
            }
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            accountService.modifyAccount(accountUpdateDTO, login);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException anfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified UUID does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not modify account");
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/add-card")
    @Operation(summary = "Add a card", description = "Add a card that will be used in payments\n\nRoles: CLIENT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Card added successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "304",
                    description = "Client already has card assigned",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("304 Not Modified"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to add client's card",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> addCard(@Valid @RequestBody AddCardDTO cardDTO) {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Account account = accountService.getAccountByLogin(login);
            Client client = (Client)accountService.getClientById(account.getId());
            paymentService.addCardToClient(cardDTO, client);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client was not found");
        } catch (CardAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        } catch (CardAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
