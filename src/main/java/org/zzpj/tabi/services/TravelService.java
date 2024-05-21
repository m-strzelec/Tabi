package org.zzpj.tabi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.repositories.ReviewRepository;
import org.zzpj.tabi.repositories.TravelRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    public Travel getTravelById(UUID id) {
        return travelRepository.findById(id).orElseThrow();
    }

    public List<Review> getTravelReviews(UUID id) {
        return reviewRepository.findAllByTravelId(id);
    }
}
