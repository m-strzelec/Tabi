package org.zzpj.tabi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
public class AccountOutputDTO {

    @Schema(example = "00000000-0000-0000-0000-000000000001")
    private UUID id;

    @Schema(example = "foo")
    private String login;

    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Doe")
    private String lastName;

    @Schema(example = "foo@email.com")
    private String email;

    @Schema(example = "CLIENT")
    private String role;

    @Schema(example = "false")
    private boolean locked;

    @Schema(example = "0")
    private Long version;
}
