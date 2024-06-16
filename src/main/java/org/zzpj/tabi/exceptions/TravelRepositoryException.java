package org.zzpj.tabi.exceptions;

public class TravelRepositoryException extends RuntimeException {
    public TravelRepositoryException() {}
    public TravelRepositoryException(String message) {
        super(message);
    }
}
