package org.zzpj.tabi.dto.account;

import java.util.UUID;

import org.zzpj.tabi.entities.Client.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientOutputDTO extends AccountOutputDTO {

    @Schema(example = "BRONZE")
    private Status status;

    public ClientOutputDTO(
        UUID id,
		String login,
		String firstName,
		String lastName,
		String email,
		String role,
		Status status,
		boolean locked,
		Long version
    ) {
        super(id, login, firstName, lastName, email, role, locked, version);
        this.status = status;
    }
}
