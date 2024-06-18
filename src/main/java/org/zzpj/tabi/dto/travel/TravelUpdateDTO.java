package org.zzpj.tabi.dto.travel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
public class TravelUpdateDTO {

    @Schema(example = "00000000-0000-0000-0001-000000000001")
    private UUID id;

    @Schema(example = "Japanese Expedition")
    private String title;

    @Schema(
        example = "Experience the sight of the rising sun and the scent of "
            + "cherry blossom"
    )
    private String description;

    @Schema(example = "0")
    private Long version;
}
