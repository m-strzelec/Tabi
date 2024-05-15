package org.zzpj.tabi.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.LoginFormDTO;
import org.zzpj.tabi.mappers.AccountMapper;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginFormDTO credentials) {
        try {
            accountService.addUser(credentials);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account with given email already exists", exception);
        }
    }

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accountList = accountService
            .getAllClients()
            .stream()
            .map(AccountMapper::toAccountDTO)
            .toList();

        return accountList;
    }
    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable("id") UUID id) {
        try {
            return AccountMapper.toAccountDTO(accountService.getClientById(id));
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account not found", exception);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") UUID id, @RequestBody LoginFormDTO account) {
        try {
            accountService.updateUserById(id, account);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account not found", exception);
        }
    }

    @PostMapping
    public ResponseEntity<?> addAccount(@RequestBody LoginFormDTO account) {
        try {
            accountService.addUser(account);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account with given email already exists", exception);
        }
    }
}
