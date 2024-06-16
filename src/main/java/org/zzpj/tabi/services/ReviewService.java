package org.zzpj.tabi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.dto.ReviewUpdateDTO;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.*;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.repositories.ReviewRepository;
import org.zzpj.tabi.repositories.TravelRepository;

import java.util.UUID;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TravelRepository travelRepository;

    public void addReview(ReviewUpdateDTO dto, String name) throws AccountNotFoundException,
            TravelNotFoundException, InvalidRatingException, ReviewAlreadyExistsException {
        if (dto.getRating() < 0 || dto.getRating() > 10) {
            throw new InvalidRatingException();
        }
        Travel travel = travelRepository.findById(dto.getTravelId()).orElseThrow(TravelNotFoundException::new);
        Client client = (Client) accountRepository.findByName(name).orElseThrow(AccountNotFoundException::new);
        if (reviewRepository.findByClientAndTravel(client, travel).isPresent()) {
            throw new ReviewAlreadyExistsException();
        }
        Review review = new Review(client, travel, dto.getComment(), dto.getRating());
        reviewRepository.save(review);
    }

    public void editReview(ReviewUpdateDTO dto, String name) throws TravelNotFoundException,
            AccountNotFoundException, ReviewNotFoundException, InvalidRatingException {
        if (dto.getRating() < 0 || dto.getRating() > 10) {
            throw new InvalidRatingException();
        }
        Client client = (Client) accountRepository.findByName(name).orElseThrow(AccountNotFoundException::new);
        Travel travel = travelRepository.findById(dto.getTravelId()).orElseThrow(TravelNotFoundException::new);
        Review review = reviewRepository.findByClientAndTravel(client, travel).orElseThrow(ReviewNotFoundException::new);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        reviewRepository.save(review);
    }

    public void deleteReview(UUID uuid, String name) throws AccountNotFoundException, TravelNotFoundException, ReviewNotFoundException {
        Client client = (Client) accountRepository.findByName(name).orElseThrow(AccountNotFoundException::new);
        Travel travel = travelRepository.findById(uuid).orElseThrow(TravelNotFoundException::new);
        Review review = reviewRepository.findByClientAndTravel(client, travel).orElseThrow(ReviewNotFoundException::new);
        reviewRepository.delete(review);
    }
}
