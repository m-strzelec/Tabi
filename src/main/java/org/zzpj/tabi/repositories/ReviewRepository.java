package org.zzpj.tabi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zzpj.tabi.entities.Review;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository  extends CrudRepository<Review, UUID> {
    Optional<Review> findById(UUID id);
    Review save(Review review);
}
