package org.zzpj.tabi.dto;

import java.util.UUID;

import org.zzpj.tabi.entities.Client.Status;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientDTO extends AccountDTO {

    private Status status;

    public ClientDTO(UUID id, String name, String email, Status status) {
        super(id, name, email);
        this.status = status;
    }
}
