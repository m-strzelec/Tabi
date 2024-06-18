package org.zzpj.tabi.exceptions;

public class ChargeException extends RuntimeException {

    private final static String msg = "Failed to charge the client";

    public ChargeException() {
        super(msg);
    }

    public ChargeException(Exception e) {
        super(msg, e);
    }
}
