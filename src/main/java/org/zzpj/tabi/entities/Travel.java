package org.zzpj.tabi.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Positive
    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Positive
    @Column(name = "max_places", nullable = false)
    private int maxPlaces;

    @Positive
    @Column(name = "available_places", nullable = false)
    private int availablePlaces;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "created_by",
        nullable = false,
        referencedColumnName = "id"
    )
    private Employee createdBy;

    @Version
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Long version = 0L;

    public Travel(
        String title,
        String description,
        String place,
        BigDecimal basePrice,
        LocalDate startDate,
        LocalDate endDate,
        int maxPlaces,
        int availablePlaces,
        Employee createdBy
    ) {
        this.title = title;
        this.description = description;
        this.place = place;
        this.basePrice = basePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxPlaces = maxPlaces;
        this.availablePlaces = availablePlaces;
        this.createdBy = createdBy;
    }
}
