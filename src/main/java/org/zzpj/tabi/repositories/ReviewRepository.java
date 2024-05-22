package org.zzpj.tabi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zzpj.tabi.entities.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository  extends CrudRepository<Review, UUID> {

    List<Review> findAllByTravelId(UUID id);
    Optional<Review> findById(UUID id);
    Review save(Review review);
}
