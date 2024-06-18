package org.zzpj.tabi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class LoginDTO {

    @Schema(example = "foo")
    @NotEmpty
    private String login;

    @Schema(example = "foo")
    @NotEmpty
    private String password;
}
