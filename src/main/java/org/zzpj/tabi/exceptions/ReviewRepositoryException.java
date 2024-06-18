package org.zzpj.tabi.exceptions;

public class ReviewRepositoryException extends RuntimeException {
    public ReviewRepositoryException() {}
    public ReviewRepositoryException(String message) {
        super(message);
    }
}
