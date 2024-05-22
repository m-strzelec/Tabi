package org.zzpj.tabi.dto.AccountDTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ChangeSelfPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
