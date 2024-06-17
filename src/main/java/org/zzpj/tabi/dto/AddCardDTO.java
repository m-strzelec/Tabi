package org.zzpj.tabi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddCardDTO {

    @Schema(example = "4242424242424242")
    @Pattern(
        regexp = "[0-9]{16}",
        message = "The card number should consists of 16 digits"
    )
    private String cardNumber;

    @Schema(example = "02")
    @Pattern(
        regexp = "1[0-1]|0[0-9]",
        message = "The expire month should be between 00 and 11"
    )
    private String expMonth;

    @Schema(example = "26")
    @Pattern(
        regexp = "[0-9]{2}",
        message = "The expire year should consists of 2 digits"
    )
    private String expYear;

    @Schema(example = "234")
    @Pattern(
        regexp = "[0-9]{3}",
        message = "The CVC should consists of 3 digits"
    )
    private String cvc;
}
