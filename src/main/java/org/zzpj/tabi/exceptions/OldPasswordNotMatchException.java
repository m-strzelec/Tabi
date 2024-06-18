package org.zzpj.tabi.exceptions;

public class OldPasswordNotMatchException extends RuntimeException {
    public OldPasswordNotMatchException() {}
    public OldPasswordNotMatchException(String message) { super(message); }
}
