package org.zzpj.tabi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository  extends CrudRepository<Review, UUID> {

    List<Review> findAllByTravelId(UUID id);
    Optional<Review> findByClientAndTravel(Client client, Travel travel);
}
