package org.zzpj.tabi.exceptions;

public class TravelWrongGuestNumberException extends TravelRepositoryException {
    public TravelWrongGuestNumberException() {
        super();
    }

    public TravelWrongGuestNumberException(String message) {
        super(message);
    }
}
