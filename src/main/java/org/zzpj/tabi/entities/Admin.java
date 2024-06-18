package org.zzpj.tabi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "admin")
@Getter @Setter
public class Admin extends Account {

    public Admin(
        String login,
        String firstName,
        String lastName,
        String email,
        String password
    ) {
        super(login, firstName, lastName, email, password);
    }
}
