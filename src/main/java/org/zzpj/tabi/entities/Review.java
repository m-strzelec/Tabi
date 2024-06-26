package org.zzpj.tabi.entities;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review", uniqueConstraints = { @UniqueConstraint(columnNames = { "client", "travel" }) })
@Getter @Setter
public class Review {

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

    @ManyToOne(cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;

    @ManyToOne(cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "travel", referencedColumnName = "id")
    private Travel travel;

    @Column
    private String comment;

    @Column
    @Min(0) @Max(10)
    private int rating;

    public Review(Client client, Travel travel, String comment, int rating) {
        this.client = client;
        this.travel = travel;
        this.comment = comment;
        this.rating = rating;
    }
}
