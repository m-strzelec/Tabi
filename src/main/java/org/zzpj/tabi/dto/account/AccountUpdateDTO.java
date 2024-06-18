package org.zzpj.tabi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
public class AccountUpdateDTO {

    @Schema(example = "00000000-0000-0000-0000-000000000001")
    private UUID id;

    @Schema(example = "Lain")
    private String firstName;

    @Schema(example = "Iwakura")
    private String lastName;

    @Schema(example = "lain@email.com")
    @Email
    private String email;

    @Schema(example = "0")
    private Long version;
}
