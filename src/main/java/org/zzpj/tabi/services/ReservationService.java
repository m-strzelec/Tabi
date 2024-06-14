package org.zzpj.tabi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Reservation;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.ReservationNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.repositories.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Reservation> getClientReservations(UUID clientId) throws AccountNotFoundException, ReservationNotFoundException {
        Client client = accountRepository.findClientById(clientId).orElseThrow(AccountNotFoundException::new);
        return reservationRepository.findByClient(client).orElseThrow(ReservationNotFoundException::new);
    }
}

