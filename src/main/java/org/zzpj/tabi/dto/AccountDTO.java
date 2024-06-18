package org.zzpj.tabi.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountDTO {

    @Schema(example = "00000000-0000-0000-0000-000000000001")
    private UUID id;
    @Schema(example = "John")
    private String firstName;
    @Schema(example = "Doe")
    private String lastName;
    @Schema(example = "foo@email.com")
    private String name;
    @Schema(example = "foo@email.com")
    private String email;
    @Schema(example = "false")
    private boolean locked;
    private String role;
    private Long version;
}
