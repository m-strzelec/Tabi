package org.zzpj.tabi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class RegisterAccountDTO {

    @Schema(example = "qux")
    @NotEmpty
    private String login;

    @Schema(example = "John")
    @NotEmpty
    private String firstName;

    @Schema(example = "Doe")
    @NotEmpty
    private String lastName;

    @Schema(example = "qux@email.com")
    @NotEmpty
    @Email
    private String email;

    @Schema(example = "password")
    @NotEmpty
    private String password;
}
