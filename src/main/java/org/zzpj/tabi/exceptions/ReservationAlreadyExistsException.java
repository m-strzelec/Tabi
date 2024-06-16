package org.zzpj.tabi.exceptions;

public class ReservationAlreadyExistsException extends RuntimeException {

    public ReservationAlreadyExistsException() {
        super("Client has already reserved this travel");
    }
}
