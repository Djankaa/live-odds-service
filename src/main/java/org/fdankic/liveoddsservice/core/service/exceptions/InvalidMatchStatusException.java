package org.fdankic.liveoddsservice.core.service.exceptions;

public class InvalidMatchStatusException extends RuntimeException {
    public InvalidMatchStatusException(String message) {
        super(message);
    }
}
