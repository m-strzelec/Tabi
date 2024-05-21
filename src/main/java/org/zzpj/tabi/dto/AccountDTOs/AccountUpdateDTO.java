package org.zzpj.tabi.dto.AccountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountUpdateDTO {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
}
