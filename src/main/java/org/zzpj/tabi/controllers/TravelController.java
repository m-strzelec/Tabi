package org.zzpj.tabi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.mappers.ReviewMapper;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.mappers.TravelMapper;
import org.zzpj.tabi.security.jws.JwsService;
import org.zzpj.tabi.services.TravelService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    @Autowired
    TravelService travelService;

    @Autowired
    JwsService jwsService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<?> createTravel(@RequestBody TravelDTO travelDTO) {
        TravelDTO createdTravel = TravelMapper.toTravelDTO(travelService.createTravel(travelDTO));
        return ResponseEntity.ok(createdTravel);
    }

    @GetMapping
    public ResponseEntity<?> getAllTravels() {
        List<TravelDTO> travels = travelService.getAllTravels().stream().map(TravelMapper::toTravelDTO).toList();
        return ResponseEntity.ok(travels);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getTravelById(@PathVariable("uuid") String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            Travel travel = travelService.getTravelById(id);
            HttpHeaders headers = new HttpHeaders();
            String etagValue = jwsService.signTravel(travel);
            headers.setETag("\"" + etagValue + "\"");
            return ResponseEntity.ok().headers(headers).body(TravelMapper.toTravelDTO(travel));
        } catch (TravelNotFoundException tnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel with specified uuid not found");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! - Could not find travel");
        }
    }

    @GetMapping("{uuid}/comments")
    public ResponseEntity<?> getTravelReviews(@PathVariable("uuid") String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            return ResponseEntity.ok().body(travelService
                    .getTravelReviews(id)
                    .stream()
                    .map(ReviewMapper::toReviewDTO)
                    .toList());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        }
    }
}
