package org.zzpj.tabi.exceptions;

public class AccountRepositoryException extends RuntimeException {
    public AccountRepositoryException() {}
    public AccountRepositoryException(String message) {
        super(message);
    }
}
