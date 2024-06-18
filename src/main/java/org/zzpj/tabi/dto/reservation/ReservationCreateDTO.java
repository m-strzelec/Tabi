package org.zzpj.tabi.dto.reservation;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservationCreateDTO {

    @Schema(example = "00000000-0000-0000-0001-000000000001")
    private UUID travelId;

    @Schema(example = "1")
    @NotEmpty
    @Positive
    private int guestCount;
}
