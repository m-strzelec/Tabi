package org.zzpj.tabi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class TravelOutputDTO {
    private UUID id;
    private String title;
    private String description;
    private String place;
    private BigDecimal basePrice;
    private LocalDate startDate;
    private LocalDate endTime;
    private int maxPlaces;
    private int availablePlaces;
    private UUID createdBy;
}
