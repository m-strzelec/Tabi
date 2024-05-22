package org.zzpj.tabi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.services.ReviewService;


@RestController
@RequestMapping("/api/v1/comments")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PreAuthorize("hasRole('CLINENT')")
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO review) {
        try {
            reviewService.addReview(review);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Travel does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong - Could not add review");
        }
    }
}
