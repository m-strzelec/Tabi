package org.zzpj.tabi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateDTO {
    @Schema(example = "5")
    private int rating;
    @Schema(example = "I've changed my mind.")
    private String comment;
    @Schema(example = "00000000-0000-0000-0001-000000000001")
    private UUID travelId;
}
