package org.zzpj.tabi.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "travel")
@Getter @Setter
public class Travel {

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

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String place;

    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(name = "guest_limit")
    private int guestLimit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Employee createdBy;
}
