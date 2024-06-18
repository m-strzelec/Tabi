package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.review.ReviewOutputDTO;
import org.zzpj.tabi.entities.Review;

public class ReviewMapper {
    static public ReviewOutputDTO toReviewDTO(Review review) {
        return new ReviewOutputDTO(review.getRating(),
                review.getComment(),
                review.getTravel().getId(),
                review.getClient().getId());
    }
}
