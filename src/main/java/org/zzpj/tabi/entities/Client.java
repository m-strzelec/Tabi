package org.zzpj.tabi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "client")
@Getter @Setter
public class Client extends Account {

    public static enum Status { BRONZE, SILVER, GOLD }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.BRONZE;

    public Client(String name, String email, String password) {
        super(name, email, password, Roles.CLIENT);
    }
}
