package org.zzpj.tabi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateDTO {
    private int rating;
    private String comment;
    private UUID travel;
}
