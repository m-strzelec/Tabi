package org.zzpj.tabi.exceptions;

public class ReservationListEmptyException extends ReservationRepositoryException {
    public ReservationListEmptyException() {
        super();
    }

    public ReservationListEmptyException(String message) {
        super(message);
    }
}

