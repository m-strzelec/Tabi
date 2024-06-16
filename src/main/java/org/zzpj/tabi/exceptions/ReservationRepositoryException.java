package org.zzpj.tabi.exceptions;

public class ReservationRepositoryException extends RuntimeException {
    public ReservationRepositoryException() {}
    public ReservationRepositoryException(String message) {
        super(message);
    }
}
