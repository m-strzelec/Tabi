package org.zzpj.tabi.services;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zzpj.tabi.dto.account.AccountUpdateDTO;
import org.zzpj.tabi.dto.account.ChangeSelfPasswordDTO;
import org.zzpj.tabi.dto.account.LoginDTO;
import org.zzpj.tabi.dto.account.RegisterAccountDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Roles;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.OldPasswordNotMatchException;
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

    public Account getAccountById(UUID id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public Account getAccountByLogin(String login) throws AccountNotFoundException {
        return accountRepository.findByLogin(login).orElseThrow(AccountNotFoundException::new);
    }

    public void registerClient(@Valid RegisterAccountDTO dto) {
        Client client = Client.builder()
                .login(dto.getLogin())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .status(Client.Status.BRONZE)
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Roles.CLIENT)
                .build();
        accountRepository.save(client);
    }

    public String login(@Valid LoginDTO credentials) {
        String login = credentials.getLogin();
        String password = credentials.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        var user = accountRepository.findByLogin(login);
        return jwtService.generateToken(user.get());
    }

    public void modifyAccount(@Valid AccountUpdateDTO accountUpdateDTO, UUID id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        if (!accountUpdateDTO.getVersion().equals(account.getVersion())) {
           throw new OptimisticLockException("Version mismatch");
        }

        account.setFirstName(accountUpdateDTO.getFirstName());
        account.setLastName(accountUpdateDTO.getLastName());
        account.setEmail(accountUpdateDTO.getEmail());

        accountRepository.save(account);
    }

    public void changePassword(@Valid ChangeSelfPasswordDTO dto, String login) throws AccountNotFoundException, OldPasswordNotMatchException {
        Account account = accountRepository.findByLogin(login).orElseThrow(AccountNotFoundException::new);

        if (!passwordEncoder.matches(dto.getOldPassword(), account.getPassword())) {
            throw new OldPasswordNotMatchException("Old password does not match the current password");
        }

        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        accountRepository.save(account);
    }
}
