package org.zzpj.tabi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddCardDTO {
    @Schema(example = "4242424242424242")
    private String cardNumber;
    @Schema(example = "02")
    private String expMonth;
    @Schema(example = "26")
    private String expYear;
    @Schema(example = "234")
    private String cvc;
}
