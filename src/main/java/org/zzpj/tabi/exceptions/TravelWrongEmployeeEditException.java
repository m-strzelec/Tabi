package org.zzpj.tabi.exceptions;

public class TravelWrongEmployeeEditException extends TravelRepositoryException {
    public TravelWrongEmployeeEditException() {
        super();
    }

    public TravelWrongEmployeeEditException(String message) {
        super(message);
    }
}
