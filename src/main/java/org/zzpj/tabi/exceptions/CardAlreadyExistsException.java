package org.zzpj.tabi.exceptions;

public class CardAlreadyExistsException extends Exception {

    public CardAlreadyExistsException() {
        super("Client already has a card assigned");
    }

    public CardAlreadyExistsException(String message) {
        super(message);
    }
}
