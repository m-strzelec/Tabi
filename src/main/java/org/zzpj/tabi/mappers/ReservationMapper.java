package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.ReservationDTO;
import org.zzpj.tabi.entities.Reservation;

public class ReservationMapper {
    
    static public ReservationDTO toReservationDTO(Reservation reservation) {
        return new ReservationDTO(
            reservation.getId(),
            reservation.getClient().getId(),
            reservation.getTravel().getId(),
            reservation.getPrice(),
            reservation.getGuestCount()
        );
    }
}

