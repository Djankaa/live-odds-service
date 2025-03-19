package org.fdankic.liveoddsservice.core.service.liveodds;

import org.fdankic.liveoddsservice.domain.Match;

public interface LiveOddsMessage {
    public String getMessageCode();
    public String formatMessage();
    public String toJson();
}
