package org.zzpj.tabi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter @Setter
public class Client extends Account {

    @Column
    private String name;

    @Column
    private String email;
}
