package org.zzpj.tabi.exceptions;

public class TravelNotFoundException extends TravelRepositoryException {
    public TravelNotFoundException() {
        super();
    }

    public TravelNotFoundException(String message) {
        super(message);
    }
}
