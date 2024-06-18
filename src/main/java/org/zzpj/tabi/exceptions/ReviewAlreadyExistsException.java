package org.zzpj.tabi.exceptions;

public class ReviewAlreadyExistsException extends ReviewRepositoryException {
    public ReviewAlreadyExistsException() {}
    public ReviewAlreadyExistsException(String message) { super(message); }
}
