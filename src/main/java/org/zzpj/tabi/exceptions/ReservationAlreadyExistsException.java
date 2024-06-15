package org.zzpj.tabi.exceptions;

public class ReservationAlreadyExistsException extends Exception {

    public ReservationAlreadyExistsException() {
        super("Client has already reserved this travel");
    }
}
