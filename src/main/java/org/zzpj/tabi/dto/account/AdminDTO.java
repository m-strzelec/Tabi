package org.zzpj.tabi.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.zzpj.tabi.dto.AccountDTO;

import java.util.UUID;
@Getter
@Setter
public class AdminDTO extends AccountDTO {
    public AdminDTO(UUID id, String firstName, String lastName, String name, String email, boolean locked, String role, Long version) {
        super(id, firstName, lastName, name, email, locked, role, version);
    }
}
