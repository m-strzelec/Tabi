package org.zzpj.tabi.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.mappers.AccountMapper;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

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
}
