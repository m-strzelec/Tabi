package org.zzpj.tabi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class TravelCreateDTO {
    @Schema(example = "00000000-0000-0000-0009-000000000009")
    private UUID id;
    @Schema(example = "China: Imperial cities and natural wonders")
    private String title;
    @Schema(example = "From the iconic Great Wall to Shanghai's soaring skyscrapers, all the way up to the 'Roof of the World', China guarantees an unforgettable experience")
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
    @Schema(example = "00000000-0000-0000-0000-000000000004")
    private UUID createdBy;
}
