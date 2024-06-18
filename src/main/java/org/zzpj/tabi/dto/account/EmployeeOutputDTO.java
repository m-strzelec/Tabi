package org.zzpj.tabi.dto.account;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeOutputDTO extends AccountOutputDTO {

    public EmployeeOutputDTO(
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
