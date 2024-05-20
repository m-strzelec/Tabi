package org.zzpj.tabi;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.zzpj.tabi.controllers.AccountController;
import org.zzpj.tabi.dto.LoginFormDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
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

    @InjectMocks
    private AccountService accountService;

    private UUID accountId;
    private Account account;
    private LoginFormDTO loginFormDTO;

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

        loginFormDTO = new LoginFormDTO();
        loginFormDTO.setName("Jane Doe");
        loginFormDTO.setEmail("jane.doe@example.com");
        loginFormDTO.setPassword("newpassword");
    }

    @Test
    public void testGetAllClients() {
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
    public void testAddUserClient() {
        //accountService.addUser(loginFormDTO, AccountController.AccountType.CLIENT);

        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        assertInstanceOf(Client.class, savedAccount);
        Client savedClient = (Client) savedAccount;
        assertEquals(loginFormDTO.getName(), savedClient.getName());
        assertEquals(loginFormDTO.getEmail(), savedClient.getEmail());
        assertEquals(loginFormDTO.getPassword(), savedClient.getPassword());
        assertEquals(Client.Status.BRONZE, savedClient.getStatus());
    }

    @Test
    public void testAddUserEmployee() {
        //accountService.addUser(loginFormDTO, AccountController.AccountType.EMPLOYEE);

        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        assertInstanceOf(Employee.class, savedAccount);
        Employee savedEmployee = (Employee) savedAccount;
        assertEquals(loginFormDTO.getName(), savedEmployee.getName());
        assertEquals(loginFormDTO.getEmail(), savedEmployee.getEmail());
        assertEquals(loginFormDTO.getPassword(), savedEmployee.getPassword());
    }

}
