package org.zzpj.tabi.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private UUID id;
    private UUID clientId;
    private UUID travelId;
    private BigDecimal price;
    private int guestCount;
}

