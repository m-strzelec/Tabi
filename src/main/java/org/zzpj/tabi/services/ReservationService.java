package org.zzpj.tabi.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Reservation;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.ChargeException;
import org.zzpj.tabi.exceptions.InvalidGuestCountException;
import org.zzpj.tabi.exceptions.NoCardFoundException;
import org.zzpj.tabi.exceptions.ReservationAlreadyExistsException;
import org.zzpj.tabi.exceptions.ReservationNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.repositories.ReservationRepository;
import org.zzpj.tabi.repositories.TravelRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TravelRepository travelRepository;

    public List<Reservation> getClientReservations(UUID clientId) throws AccountNotFoundException, ReservationNotFoundException {
        Client client = accountRepository.findClientById(clientId).orElseThrow(AccountNotFoundException::new);
        return reservationRepository.findByClient(client).orElseThrow(ReservationNotFoundException::new);
    }

    @Transactional
    public void createReservation(Client client, Travel travel, int guestCount) throws NoCardFoundException, ChargeException, ReservationAlreadyExistsException, InvalidGuestCountException {
        if (guestCount <= 0) {
            throw new InvalidGuestCountException();
        }
        if (reservationRepository.findByClientAndTravel(client, travel).isPresent()) {
            throw new ReservationAlreadyExistsException();
        }
        if (travel.getAvailablePlaces() < guestCount) {
            throw new InvalidGuestCountException("Not enough available places");
        }
        BigDecimal amount = travel.getBasePrice().multiply(new BigDecimal(guestCount));
        String title = "travel " + travel.getId();
        travel.setAvailablePlaces(travel.getAvailablePlaces() - guestCount);
        if (!travel.getVersion().equals(travelRepository.findById(travel.getId()).orElseThrow().getVersion())) {
            travelRepository.save(travel);
        }
        paymentService.chargeClient(client, amount, title);
    }
}

