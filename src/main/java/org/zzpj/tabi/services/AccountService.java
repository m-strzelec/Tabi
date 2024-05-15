package org.zzpj.tabi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllClients() {
        return accountRepository.findAll();
    }

    public Account getClientById(UUID id) {
        return accountRepository.findById(id).orElseThrow();
    }
}
