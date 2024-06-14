package org.zzpj.tabi.exceptions;

public class ReservationNotFoundException extends ReservationRepositoryException {
    public ReservationNotFoundException() {
        super();
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }
}

