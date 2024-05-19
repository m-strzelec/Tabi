package org.zzpj.tabi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.controllers.AccountController;
import org.zzpj.tabi.dto.LoginFormDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Roles;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.security.JwtService;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<Account> getAllClients() {
        return accountRepository.findAll();
    }

    public Account getClientById(UUID id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public Account getClientByLogin(String login) {return accountRepository.findByName(login).orElseThrow();}

    public void updateUserById(UUID id, LoginFormDTO dto) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setName(dto.getName());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());
        accountRepository.save(account);
    }

    public void addUser(LoginFormDTO dto, AccountController.AccountType type) {
        if (type == AccountController.AccountType.CLIENT) {
            Client client = Client.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .status(Client.Status.BRONZE)
                    .password(dto.getPassword()).build();
            accountRepository.save(client);
        } else if (type == AccountController.AccountType.EMPLOYEE) {
            Employee employee = Employee.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(dto.getPassword()).build();
            accountRepository.save(employee);
        }
    }

    public String addUser2(LoginFormDTO dto, Roles roles) {
        if (roles == Roles.CLIENT) {
            Client client = Client.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .status(Client.Status.BRONZE)
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(Roles.CLIENT)
                    .build();
            accountRepository.save(client);
            return "created";
        } else if (roles == Roles.EMPLOYEE) {
            Employee employee = Employee.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(dto.getPassword()).build();
            accountRepository.save(employee);
            return "created";
        }
        return null;
    }

}
