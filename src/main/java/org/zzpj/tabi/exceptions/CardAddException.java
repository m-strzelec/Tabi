package org.zzpj.tabi.exceptions;

public class CardAddException extends RuntimeException {

    private final static String msg = "Failed to add client's card";

    public CardAddException() {
        super(msg);
    }

    public CardAddException(Exception e) {
        super(msg, e);
    }
}
