package org.zzpj.tabi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.repositories.ReviewRepository;
import org.zzpj.tabi.repositories.TravelRepository;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TravelRepository travelRepository;

    public void addReview(ReviewDTO dto) throws AccountNotFoundException, TravelNotFoundException {
        Travel travel = travelRepository.findById(dto.getTravel()).orElseThrow(TravelNotFoundException::new);
        Client client = (Client) accountRepository.findById(dto.getClient()).orElseThrow(AccountNotFoundException::new);
        Review review = new Review(client, travel, dto.getComment(), dto.getRating());
        reviewRepository.save(review);
    }
}
