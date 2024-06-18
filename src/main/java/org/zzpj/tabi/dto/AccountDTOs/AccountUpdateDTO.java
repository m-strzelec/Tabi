package org.zzpj.tabi.dto.AccountDTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class AccountUpdateDTO {
    @Schema(example = "00000000-0000-0000-0000-000000000001")
    private UUID id;
    @Schema(example = "xyzzy")
    private String login;
    @Schema(example = "xyz")
    private String firstName;
    @Schema(example = "zyx")
    private String lastName;
    @Schema(example = "xyzzy@example.com")
    private String email;
    private Long version;
}
