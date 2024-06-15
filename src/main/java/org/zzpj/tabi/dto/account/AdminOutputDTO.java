package org.zzpj.tabi.dto.account;

import org.zzpj.tabi.dto.AccountDTO;

import java.util.UUID;

public class AdminOutputDTO extends AccountDTO {
    public AdminOutputDTO(UUID id, String firstName, String lastName, String name, String email, boolean locked) {
        super(id, firstName, lastName, name, email, locked);
    }
}
