package org.zzpj.tabi.exceptions;

public class InvalidRatingException extends ReviewRepositoryException {
    public InvalidRatingException() {}
    public InvalidRatingException(String message) {
        super(message);
    }
}
