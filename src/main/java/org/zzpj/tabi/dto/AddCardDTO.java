package org.zzpj.tabi.dto;

import lombok.Data;

@Data
public class AddCardDTO {

    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvc;
}
