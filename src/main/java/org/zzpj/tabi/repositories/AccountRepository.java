package org.zzpj.tabi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.zzpj.tabi.entities.Account;

public interface AccountRepository extends CrudRepository<Account, UUID> {

    List<Account> findAll();
}
