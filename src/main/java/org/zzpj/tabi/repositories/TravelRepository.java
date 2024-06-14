package org.zzpj.tabi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.zzpj.tabi.entities.Travel;

import java.util.List;
import java.util.UUID;

public interface TravelRepository extends CrudRepository<Travel, UUID> {

    @NonNull
    List<Travel> findAll();
}
