package org.zzpj.tabi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.zzpj.tabi.dto.account.RegisterAccountDTO;
import org.zzpj.tabi.dto.review.*;
import org.zzpj.tabi.dto.travel.*;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.*;
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
                            schema = @Schema(implementation = TravelCreateDTO.class))}
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
    public ResponseEntity<?> createTravel(@Valid @RequestBody TravelCreateDTO travelCreateDTO) {
        try{
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Account account = accountService.getAccountByLogin(login);
            TravelOutputDTO createdTravel = TravelMapper.toTravelDTO(travelService.createTravel(travelCreateDTO, account.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTravel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: New travel could not be created");
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all travels", description = "Get all travels from system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all travels",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TravelOutputDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No travels were found",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("204 No Content"))}
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
            List<TravelOutputDTO> travels = travelService.getAllTravels().stream().map(TravelMapper::toTravelDTO).toList();
            if (!travels.isEmpty()) {
                return ResponseEntity.ok(travels);
            }
            return ResponseEntity.noContent().build();
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
                            schema = @Schema(implementation = TravelCreateDTO.class))}
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
    public ResponseEntity<?> getTravelById(@PathVariable("uuid") UUID uuid) {
        try {
            Travel travel = travelService.getTravelById(uuid);
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

    @GetMapping("/{uuid}/reviews")
    @Operation(summary = "Get travel reviews", description = "Get all reviews for particular travel")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found reviews",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewOutputDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Other problems occurred e.g. database connection error",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("500 Internal Server Error"))}
            )
    })
    public ResponseEntity<?> getTravelReviews(@PathVariable("uuid") UUID uuid) {
        try {
            return ResponseEntity.ok().body(travelService
                    .getTravelReviews(uuid)
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
    @PostMapping("/reviews/add")
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
                    description = "Invalid rating or review already exists",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Travel or account does not exist",
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
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewUpdateDTO review) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            reviewService.addReview(review, name);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exist");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel does not exist");
        } catch (InvalidRatingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rating points should be integers within the range [0, 10]");
        } catch (ReviewAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review from this account already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not add review");

        }
    }


    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/reviews")
    @Operation(summary = "Edit review", description = "Edit review to the travel\n\nRoles: CLIENT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Review edited",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid rating",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("400 Bad Request"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Travel, review or account does not exist",
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
    public ResponseEntity<?> editReview(@Valid @RequestBody ReviewUpdateDTO review) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            reviewService.editReview(review, name);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exist");
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review does not exist");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel does not exist");
        } catch (InvalidRatingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rating points should be integers within the range [0, 10]");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not add review");
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("{uuid}/reviews")
    @Operation(summary = "Delete review", description = "Delete review\n\nRoles: CLIENT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Review deleted",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Travel, review or account does not exist",
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
    public ResponseEntity<?> deleteReview(@PathVariable("uuid") UUID uuid) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            reviewService.deleteReview(uuid, name);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exist");
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review does not exist");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not add review");
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping
    @Operation(summary = "Edit travel", description = "Edit travel\n\nRoles: EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Travel edited",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Wrong employee",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("403 Forbidden"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Travel or account does not exist",
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
    public ResponseEntity<?> editTravel(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch, @RequestBody TravelUpdateDTO travel) {
        try {
            if (ifMatch == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-match header is required");
            }
            if (!jwsService.isIfMatchValidTravel(ifMatch, travel)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("If-match header is invalid");
            }

            String employeeLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            travelService.editTravel(travel, employeeLogin);
            return ResponseEntity.ok().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exist");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel does not exist");
        } catch (OptimisticLockException ole) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data has just been modified - HAZARD");
        } catch (TravelWrongEmployeeEditException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Travel can be edited only by employee who created travel");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: Could not add review");
        }
    }
}
