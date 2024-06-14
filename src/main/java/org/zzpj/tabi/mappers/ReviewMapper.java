package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.entities.Review;

public class ReviewMapper {
    static public ReviewDTO toReviewDTO(Review review) {
        return new ReviewDTO(review.getRating(),
                review.getComment(),
                review.getTravel().getId(),
                review.getClient().getId());
    }
}
