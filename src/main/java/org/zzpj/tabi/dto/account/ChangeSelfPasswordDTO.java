package org.zzpj.tabi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ChangeSelfPasswordDTO {

    @Schema(example = "foo")
    private String oldPassword;

    @Schema(example = "oof")
    private String newPassword;
}
