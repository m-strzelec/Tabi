package org.zzpj.tabi.dto.travel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter @Setter
public class TravelCreateDTO {

    @Schema(example = "China: Imperial cities and natural wonders")
    private String title;

    @Schema(
        example = "From the iconic Great Wall to Shanghai's soaring "
            + "skyscrapers, all the way up to the 'Roof of the World', China "
            + "guarantees an unforgettable experience"
    )
    private String description;

    @Schema(example = "China")
    private String place;

    @Schema(example = "4999.99")
    private BigDecimal basePrice;

    @Schema(example = "2025-12-15")
    private LocalDate startDate;

    @Schema(example = "2025-12-25")
    private LocalDate endDate;

    @Schema(example = "100")
    private int maxPlaces;

    @Schema(example = "10")
    private int available;
}
