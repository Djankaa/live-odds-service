package org.fdankic.liveoddsservice.core.service.liveodds;

import org.fdankic.liveoddsservice.domain.Match;

public interface LiveOddsMessage {
    String getMessageCode();
    String toJson();
}
