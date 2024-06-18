package org.zzpj.tabi.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeDTO extends AccountDTO {
    public EmployeeDTO(UUID id, String firstName, String lastName, String name, String email, boolean locked, String role, Long version) {
        super(id, firstName, lastName, name, email, locked, role, version);
    }
}
