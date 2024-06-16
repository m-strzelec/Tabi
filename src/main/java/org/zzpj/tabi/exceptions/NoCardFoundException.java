package org.zzpj.tabi.exceptions;

public class NoCardFoundException extends RuntimeException {

    public NoCardFoundException() {
        super("The client has no card assigned");
    }
}
