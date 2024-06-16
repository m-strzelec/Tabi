package org.zzpj.tabi.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    @Schema(example = "00000000-0000-0001-0000-000000000001")
    private UUID id;
    @Schema(example = "00000000-0000-0000-0000-000000000001")
    private UUID clientId;
    @Schema(example = "00000000-0000-0000-0001-000000000001")
    private UUID travelId;
    @Schema(example = "2044.50")
    private BigDecimal price;
    @Schema(example = "1")
    private int guestCount;
}

