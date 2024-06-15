package org.zzpj.tabi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin extends Account {
    public Admin(String name, String firstName, String lastName, String email, String password) {
        super(name, firstName, lastName, email, password);
    }
}
