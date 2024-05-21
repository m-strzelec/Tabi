package org.zzpj.tabi.entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "review")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "travel", referencedColumnName = "id")
    private Travel travel;

    @Column
    private String comment;

    @Column
    private int rating;

    public Review(Client client, Travel travel, String comment, int rating) {
        this.client = client;
        this.travel = travel;
        this.comment = comment;
        this.rating = rating;
    }
}
