package org.zzpj.tabi.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private boolean locked;
    private String role;
    private Long version;
}
