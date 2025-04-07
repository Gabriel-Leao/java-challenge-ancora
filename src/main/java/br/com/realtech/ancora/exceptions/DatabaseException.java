package br.com.realtech.ancora.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
