package org.zzpj.tabi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TravelDTO {
    private UUID id;
    private String title;
    private String description;
    private String place;
    private BigDecimal basePrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestLimit;
    private UUID createdBy;
}
