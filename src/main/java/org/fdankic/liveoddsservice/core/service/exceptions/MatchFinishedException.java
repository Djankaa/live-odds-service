package org.fdankic.liveoddsservice.core.service.exceptions;

public class MatchFinishedException extends RuntimeException {
    public MatchFinishedException(String message) {
        super(message);
    }
}
