package org.zzpj.tabi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.LoginFormDTO;
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

    public void updateUserById(UUID id, LoginFormDTO dto) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setName(dto.getName());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());
        accountRepository.save(account);
    }

    public void addUser(LoginFormDTO dto) {
        Account account = new Account();
        account.setName(dto.getName());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());
        accountRepository.save(account);
    }
}
