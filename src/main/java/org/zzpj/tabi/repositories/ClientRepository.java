package org.zzpj.tabi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.zzpj.tabi.entities.Client;

public interface ClientRepository extends CrudRepository<Client, UUID> {

    List<Client> findAll();
}
