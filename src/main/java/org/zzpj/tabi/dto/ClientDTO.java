package org.zzpj.tabi.dto;

import java.util.UUID;

import org.zzpj.tabi.entities.Client.Status;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientDTO extends AccountDTO {

    private Status status;

    public ClientDTO(UUID id, String firstName, String lastName, String name, String email, boolean locked, Status status) {
        super(id, firstName, lastName, name, email, locked);
        this.status = status;
    }
}
