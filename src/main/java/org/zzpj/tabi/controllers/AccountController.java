package org.zzpj.tabi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
