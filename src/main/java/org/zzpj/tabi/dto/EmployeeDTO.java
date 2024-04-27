package org.zzpj.tabi.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeDTO extends AccountDTO {

    public EmployeeDTO(UUID id, String name, String email) {
        super(id, name, email);
    }
}
