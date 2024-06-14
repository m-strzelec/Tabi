package org.zzpj.tabi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, UUID> {
    
    Optional<List<Reservation>> findByClient(Client client);
}
