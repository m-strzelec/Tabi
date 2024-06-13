package org.zzpj.tabi.entities;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "reservation",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniqueClientAndTravel",
            columnNames = {"client", "travel"}
        )
    }
)
@Getter @Setter
public class Reservation {

    @Id
    @Column(
        name = "id",
        columnDefinition = "UUID",
        unique = true,
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "travel", referencedColumnName = "id")
    private Travel travel;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "guest_count")
    private int guestCount;
}
