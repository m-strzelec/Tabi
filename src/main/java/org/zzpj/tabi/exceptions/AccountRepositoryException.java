package org.zzpj.tabi.exceptions;

public class AccountRepositoryException extends Exception {
    public AccountRepositoryException() {}
    public AccountRepositoryException(String message) {
        super(message);
    }
}
