package org.zzpj.tabi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.mappers.ReviewMapper;
import org.zzpj.tabi.mappers.TravelMapper;
import org.zzpj.tabi.services.TravelService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    @Autowired
    TravelService travelService;

    @GetMapping
    public List<TravelDTO> getTravels() {
        return travelService
                .getAllTravels()
                .stream()
                .map(TravelMapper::toTravelDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public TravelDTO getTravelById(@PathVariable("id") UUID id) {
        try {
            return TravelMapper.toTravelDTO(travelService.getTravelById(id));
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Travel not found", exception);
        }
    }

    @GetMapping("{id}/comments")
    public List<ReviewDTO> getTravelReviews(@PathVariable("id") UUID id) {
        return travelService
                .getTravelReviews(id)
                .stream()
                .map(ReviewMapper::toReviewDTO)
                .toList();
    }
}
