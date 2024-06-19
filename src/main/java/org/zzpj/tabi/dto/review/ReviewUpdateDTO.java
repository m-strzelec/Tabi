package org.zzpj.tabi.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
public class ReviewUpdateDTO {

    @Schema(example = "5")
    @Min(0) @Max(10)
    private int rating;

    @Schema(example = "I've changed my mind.")
    private String comment;

    @Schema(example = "00000000-0000-0000-0001-000000000001")
    private UUID travelId;
}
