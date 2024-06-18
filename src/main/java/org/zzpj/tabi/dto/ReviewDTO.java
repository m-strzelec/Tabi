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
public class ReviewDTO {
    @Schema(example = "10")
    private int rating;
    @Schema(example = "Unforgettable experience")
    private String comment;
    @Schema(example = "00000000-0000-0000-0001-000000000001")
    private UUID travel;
    @Schema(example = "00000000-0000-0000-0000-000000000001")
    private UUID client;
}
