package org.zzpj.tabi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;

public interface AccountRepository extends CrudRepository<Account, UUID> {

    @NonNull
    List<Account> findAll();
    Optional<Account> findByLogin(String name);
    Optional<Account> findByEmail(String email);
    Optional<Client> findClientById(@NotNull UUID id);
    Optional<Employee> findEmployeeById(@NotNull UUID id);
}
