package org.zzpj.tabi.exceptions;

public class NoCardFoundException extends Exception {

    public NoCardFoundException() {
        super("The client has no card assigned");
    }
}
