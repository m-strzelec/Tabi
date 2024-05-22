package org.zzpj.tabi.dto.AccountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class AccountUpdateDTO {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
}
