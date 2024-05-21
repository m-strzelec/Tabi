package org.zzpj.tabi.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.mappers.AccountMapper;
import org.zzpj.tabi.security.jws.JwsService;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwsService jwsService;

    @GetMapping
    @Operation(summary = "Get all accounts", description = "Get all accounts from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all accounts"),
            @ApiResponse(responseCode = "500", description = "Other problems occurred eg. database error")
    })
    public ResponseEntity<?> getAllAccounts() {
        log.info("Jestem w get all accounts");
        try {
            List<AccountDTO> accountList = accountService
                    .getAllAccounts()
                    .stream()
                    .map(AccountMapper::toAccountDTO)
                    .toList();
            log.info(accountList.toString());
            return ResponseEntity.ok().body(accountList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Could not find accounts");
        }
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get account by uuid", description = "Get account with specified uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found account with specified uuid"),
            @ApiResponse(responseCode = "400", description = "Uuid is invalid - invalid format"),
            @ApiResponse(responseCode = "404", description = "User with specified uuid doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Other problems eg. database error")
    })
    public ResponseEntity<?> getAccountById(@PathVariable("uuid") String uuid) {
        try {
            //parsuj do UUID jesli zle utworzony uuid to zlap wyjatek i wyswietl informacje
            log.info(uuid);
            UUID id = UUID.fromString(uuid);
            //jesli nie rzuci wyjatku to pobierz uzytkownika z bazy - tu moze rzucic wyjatek ale jest przechwytywany
            Account account = accountService.getClientById(id);
            //wygeneruj etag dla klienta
            String etagValue = jwsService.signAccount(account);
            AccountDTO accountDTO = AccountMapper.toAccountDTO(accountService.getClientById(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setETag("\"" + etagValue + "\"");
            //wyslij odpowiedz z naglowkiem etag
            return ResponseEntity.ok().headers(headers).body(accountDTO);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with specified uuid doesnt exist");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Could not find account");
        }
    }

    @PutMapping
    @Operation(summary = "Edit account", description = "Edit account with specified uuid", parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "If-Match", description = "ETag for conditional requests", required = false)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account modified successfully"),
            @ApiResponse(responseCode = "400", description = "If-mach header invalid"),
            @ApiResponse(responseCode = "404", description = "Account doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Other problems occurred eg. database connection error")
    })
    public ResponseEntity<?> updateAccount(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        try {
            if (ifMatch == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-mach header is required");
            }
            if (!jwsService.isIfMatchValid(ifMatch, accountUpdateDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-mach header is invalid");
            }
            accountService.modifyAccount(accountUpdateDTO);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException anfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified uuid doesn't exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Culd not modify account");
        }
    }
}
