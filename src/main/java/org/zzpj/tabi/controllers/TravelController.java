package org.zzpj.tabi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zzpj.tabi.dto.ReviewDTO;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Roles;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.mappers.ReviewMapper;
import org.zzpj.tabi.mappers.TravelMapper;
import org.zzpj.tabi.security.jws.JwsService;
import org.zzpj.tabi.services.AccountService;
import org.zzpj.tabi.services.ReviewService;
import org.zzpj.tabi.services.TravelService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    @Autowired
    TravelService travelService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    AccountService accountService;

    @Autowired
    JwsService jwsService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    @Operation(summary = "Create travel", description = "Add new travel to the system\n\nRoles: EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Travel created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TravelDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> createTravel(@RequestBody TravelDTO travelDTO) {
        try{
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Account account = accountService.getAccountByLogin(login);
            TravelDTO createdTravel = TravelMapper.toTravelDTO(travelService.createTravel(travelDTO, account.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTravel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: New travel could not be created");
        }
    }

    @GetMapping
    @Operation(summary = "Get all travels", description = "Get all travels from system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all travels",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TravelDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> getAllTravels() {
        try {
            List<TravelDTO> travels = travelService.getAllTravels().stream().map(TravelMapper::toTravelDTO).toList();
            return ResponseEntity.ok(travels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not find travels");
        }
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get travel by UUID", description = "Get travel by UUID from system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found travel with specified UUID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TravelDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "UUID has invalid format",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Travel with specified UUID does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("404 Not Found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> getTravelById(@PathVariable("uuid") String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            Travel travel = travelService.getTravelById(id);
            HttpHeaders headers = new HttpHeaders();
            String etagValue = jwsService.signTravel(travel);
            headers.setETag("\"" + etagValue + "\"");
            return ResponseEntity.ok().headers(headers).body(TravelMapper.toTravelDTO(travel));
        } catch (TravelNotFoundException tnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel with specified UUID not found");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not find travel");
        }
    }

    @GetMapping("/{uuid}/comments")
    @Operation(summary = "Get travel reviews", description = "Get all reviews for particular travel")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found reviews",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "UUID has invalid format",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not find reviews for the given travel");
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/comments/add")
    @Operation(summary = "Add review", description = "Add review to the travel\n\nRoles: CLIENT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Review added",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Travel or account does not exist",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO review) {
        try {
            reviewService.addReview(review);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Travel does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not add review");
        }
    }
}
