package org.zzpj.tabi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.dto.AccountDTOs.ChangeSelfPasswordDTO;
import org.zzpj.tabi.dto.LoginDTO;
import org.zzpj.tabi.dto.LoginFormDTO;
import org.zzpj.tabi.dto.RegisterAccountDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Roles;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.security.JwtService;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getClientById(UUID id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public Account getAccountByLogin(String login) throws AccountNotFoundException {
        return accountRepository.findByName(login).orElseThrow(AccountNotFoundException::new);
    }

    public void updateUserById(UUID id, LoginFormDTO dto) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setName(dto.getName());
        account.setEmail(dto.getEmail());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        accountRepository.save(account);
    }

    public void registerClient(RegisterAccountDTO dto) {
        Client client = Client.builder()
                .name(dto.getName())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .status(Client.Status.BRONZE)
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Roles.CLIENT)
                .build();
        accountRepository.save(client);
    }

    public String login(LoginDTO credentials) {
        String login = credentials.getName();
        String password = credentials.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        var user = accountRepository.findByName(login);
        return jwtService.generateToken(user.get());
    }

    public void modifyAccount(AccountUpdateDTO accountUpdateDTO) throws AccountNotFoundException {
        Account account = accountRepository.findByName(accountUpdateDTO.getLogin()).orElseThrow(AccountNotFoundException::new);
        account.setFirstName(accountUpdateDTO.getFirstName());
        account.setLastName(accountUpdateDTO.getLastName());
        accountRepository.save(account);
    }

    public void changePassword(ChangeSelfPasswordDTO dto) throws AccountNotFoundException {
        UUID accountId = dto.getId();
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        if (!passwordEncoder.matches(dto.getOldPassword(), account.getPassword())) {
            throw new IllegalArgumentException("Old password doesn't match current password");
        }
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountRepository.save(account);
    }
}
