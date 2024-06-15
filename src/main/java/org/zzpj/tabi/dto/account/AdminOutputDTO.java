package org.zzpj.tabi.dto.account;

import java.util.UUID;

public class AdminOutputDTO extends AccountOutputDTO {
    public AdminOutputDTO(UUID id, String firstName, String lastName, String login, String email, boolean locked, String role) {
        super(id, firstName, lastName, login, email, locked, role);
    }
}
