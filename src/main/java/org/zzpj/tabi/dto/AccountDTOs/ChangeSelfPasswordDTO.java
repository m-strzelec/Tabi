package org.zzpj.tabi.dto.AccountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeSelfPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
