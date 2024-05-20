package org.zzpj.tabi.exceptions;

public class AccountNotFoundException extends AccountRepositoryException {
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
