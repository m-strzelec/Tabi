package org.zzpj.tabi.exceptions;

public class InvalidGuestCountException extends RuntimeException {

    public InvalidGuestCountException() {
        super("Invalid guest count");
    }
}
