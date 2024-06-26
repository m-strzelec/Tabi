package org.zzpj.tabi.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class AdminOutputDTO extends AccountOutputDTO {

    public AdminOutputDTO(
        UUID id,
		String login,
		String firstName,
		String lastName,
		String email,
		String role,
		boolean locked,
		Long version
    ) {
        super(id, login, firstName, lastName, email, role, locked, version);
    }
}
