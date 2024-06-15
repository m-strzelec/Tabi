package org.zzpj.tabi.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ReservationCreateDTO {

    private UUID travelId;
    private int guestCount;
}
