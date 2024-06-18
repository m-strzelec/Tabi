package org.zzpj.tabi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class RegisterAccountDTO {

    @Schema(example = "qux")
    private String login;

    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Doe")
    private String lastName;

    @Schema(example = "qux@example.com")
    private String email;

    @Schema(example = "password")
    private String password;
}
