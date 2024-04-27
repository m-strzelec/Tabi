package org.zzpj.tabi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<String> getAllClients() {
        List<Account> accountList = accountService.getAllClients();
        List<String> accountListDTO = new ArrayList<>();

        for (Account account : accountList) {
            accountListDTO.add(
                account.getId().toString()
                + " " + account.getName()
                + " " + account.getEmail()
                + " " + account.getClass()
            );
        }

        return accountListDTO;
    }
}
