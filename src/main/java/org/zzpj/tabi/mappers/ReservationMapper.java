package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.reservation.ReservationOutputDTO;
import org.zzpj.tabi.entities.Reservation;

public class ReservationMapper {
    
    static public ReservationOutputDTO toReservationDTO(Reservation reservation) {
        return new ReservationOutputDTO(
            reservation.getId(),
            reservation.getClient().getId(),
            reservation.getTravel().getId(),
            reservation.getPrice(),
            reservation.getGuestCount()
        );
    }
}

