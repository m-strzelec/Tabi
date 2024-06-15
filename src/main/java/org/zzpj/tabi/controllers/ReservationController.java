package org.zzpj.tabi.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzpj.tabi.dto.ReservationCreateDTO;
import org.zzpj.tabi.dto.ReservationDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.ReservationListEmptyException;
import org.zzpj.tabi.exceptions.ReservationNotFoundException;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.mappers.ReservationMapper;
import org.zzpj.tabi.services.AccountService;
import org.zzpj.tabi.services.PaymentService;
import org.zzpj.tabi.services.ReservationService;
import org.zzpj.tabi.services.TravelService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/reservtions")
public class ReservationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TravelService travelService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    @Operation(summary = "Get own reservations", description = "Get all of own reservations\n\nRoles: CLIENT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all travels",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No reservations were found",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("204 No Content"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Given user was not found",
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
    public ResponseEntity<?> getOwnReservations() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Account account = accountService.getAccountByLogin(login);
            List<ReservationDTO> reservations = reservationService
                .getClientReservations(account.getId())
                .stream()
                .map(ReservationMapper::toReservationDTO)
                .toList();
            if (reservations.isEmpty()) {
                throw new ReservationListEmptyException();
            }
            return ResponseEntity.ok().body(reservations);
        } catch (ReservationListEmptyException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No reservations were found");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified UUID does not exist");
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find reservations");
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/create")
    @Operation(summary = "Create a reservation", description = "Create a reservation for a given trip and pay for it\n\nRoles: CLIENT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservation created successfully",
                    content = {@Content(mediaType = "text/plain",
                            examples = @ExampleObject("200 OK"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account or travel does not exist",
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
    public ResponseEntity<?> createReservation(ReservationCreateDTO reservationCreateDTO) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Account account = accountService.getAccountByLogin(login);
            Client client = (Client)accountService.getClientById(account.getId());
            Travel travel = travelService.getTravelById(reservationCreateDTO.getTravelId());
            reservationService.createReservation(client, travel, reservationCreateDTO.getGuestCount());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client was not found");
        } catch (TravelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel was not found");
        }
        return ResponseEntity.ok().build();
    }
}
