package org.zzpj.tabi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;

public interface AccountRepository extends CrudRepository<Account, UUID> {

    List<Account> findAll();
    Optional<Account> findById(@NotNull UUID id);
    Optional<Account> findByName(String name);
    Optional<Account> findByEmail(String email);
    Optional<Client> findClientById(@NotNull UUID id);
    Optional<Employee> findEmployeeById(@NotNull UUID id);
    Account save(Account account);
}
