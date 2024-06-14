package org.zzpj.tabi.exceptions;

public class ReviewNotFoundException extends ReviewRepositoryException {
    public ReviewNotFoundException() {
        super();
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
