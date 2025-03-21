package org.fdankic.liveoddsservice.core.service.liveodds;

public interface LiveOddsMessage {
    String getMessageCode();
    String toJson();
}
