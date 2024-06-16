package org.zzpj.tabi.dto.AccountDTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeSelfPasswordDTO {
    @Schema(example = "foo")
    private String oldPassword;
    @Schema(example = "oof")
    private String newPassword;
}
