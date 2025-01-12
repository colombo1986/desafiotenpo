package org.tenpo.challenge.cvergara.exception;


public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }
}
