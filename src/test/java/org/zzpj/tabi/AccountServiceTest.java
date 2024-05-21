package org.zzpj.tabi;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.dto.LoginDTO;
import org.zzpj.tabi.dto.LoginFormDTO;
import org.zzpj.tabi.dto.RegisterAccountDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Roles;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.security.JwtService;
import org.zzpj.tabi.services.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AccountService accountService;

    private UUID accountId;
    private Account account;
    private Client client;
    private LoginFormDTO loginFormDTO;
    private RegisterAccountDTO registerAccountDTO;
    private LoginDTO loginDTO;
    private AccountUpdateDTO accountUpdateDTO;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @BeforeEach
    public void setUp() {
        accountId = UUID.randomUUID();
        account = new Account();
        account.setId(accountId);
        account.setName("John Doe");
        account.setEmail("john.doe@example.com");
        account.setPassword("password");

        client = Client.builder()
                .name("Jane Doe")
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .status(Client.Status.BRONZE)
                .password("password123")
                .role(Roles.CLIENT)
                .build();

        loginFormDTO = new LoginFormDTO();
        loginFormDTO.setName("Jane Doe");
        loginFormDTO.setEmail("jane.doe@example.com");
        loginFormDTO.setPassword("newpassword123");

        registerAccountDTO = new RegisterAccountDTO();
        registerAccountDTO.setName("Jane Doe");
        registerAccountDTO.setFirstName("Jane");
        registerAccountDTO.setLastName("Doe");
        registerAccountDTO.setEmail("jane.doe@example.com");
        registerAccountDTO.setPassword("password123");

        loginDTO = new LoginDTO();
        loginDTO.setName("John Doe");
        loginDTO.setPassword("password123");

        accountUpdateDTO = new AccountUpdateDTO(
                "John Doe", "John", "Doe", "john.doe@example.com"
        );
    }

    @Test
    public void testGetAllAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(account));

        List<Account> accounts = accountService.getAllAccounts();

        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals(account.getId(), accounts.getFirst().getId());
    }

    @Test
    public void testGetClientById() throws AccountNotFoundException {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getClientById(accountId);

        assertNotNull(foundAccount);
        assertEquals(accountId, foundAccount.getId());
    }

    @Test
    public void testGetAccountByLogin() throws AccountNotFoundException {
        when(accountRepository.findByName("John Doe")).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getAccountByLogin("John Doe");

        assertNotNull(foundAccount);
        assertEquals("John Doe", foundAccount.getName());
    }

    @Test
    public void testUpdateUserById() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.updateUserById(accountId, loginFormDTO);

        verify(accountRepository).save(accountCaptor.capture());
        Account updatedAccount = accountCaptor.getValue();

        assertEquals(loginFormDTO.getName(), updatedAccount.getName());
        assertEquals(loginFormDTO.getEmail(), updatedAccount.getEmail());
        assertEquals(loginFormDTO.getPassword(), updatedAccount.getPassword());
    }

    @Test
    public void testRegisterClient() {
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        accountService.registerClient(registerAccountDTO);

        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        assertInstanceOf(Client.class, savedAccount);
        Client savedClient = (Client) savedAccount;
        assertEquals(registerAccountDTO.getName(), savedClient.getName());
        assertEquals(registerAccountDTO.getFirstName(), savedClient.getFirstName());
        assertEquals(registerAccountDTO.getLastName(), savedClient.getLastName());
        assertEquals(registerAccountDTO.getEmail(), savedClient.getEmail());
        assertEquals("encodedPassword", savedClient.getPassword());
        assertEquals(Client.Status.BRONZE, savedClient.getStatus());
        assertEquals(Roles.CLIENT, savedClient.getRole());
    }

    @Test
    public void testLogin() {
        when(accountRepository.findByName("John Doe")).thenReturn(Optional.of(account));
        when(jwtService.generateToken(account)).thenReturn("jwtToken");

        String token = accountService.login(loginDTO);

        assertEquals("jwtToken", token);
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("John Doe", "password123")
        );
    }

    @Test
    public void testModifyAccount() throws AccountNotFoundException {
        when(accountRepository.findByName("John Doe")).thenReturn(Optional.of(account));

        accountService.modifyAccount(accountUpdateDTO);

        verify(accountRepository).save(accountCaptor.capture());
        Account updatedAccount = accountCaptor.getValue();

        assertEquals(accountUpdateDTO.getFirstName(), updatedAccount.getFirstName());
        assertEquals(accountUpdateDTO.getLastName(), updatedAccount.getLastName());
    }

}
