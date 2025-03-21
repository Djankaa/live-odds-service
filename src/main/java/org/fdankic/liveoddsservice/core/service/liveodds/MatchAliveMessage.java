package org.fdankic.liveoddsservice.core.service.liveodds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchAliveMessage implements LiveOddsMessage {
    @JsonProperty
    final String messageCode = LiveOddsMessageCode.MATCH_ALIVE;

    @JsonProperty
    private final String matchId;

    @JsonProperty
    private final int duration = 1;

    @Override
    public String getMessageCode() {
        return this.messageCode;
    }

    @Override
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // You can return an empty string or handle the exception as needed
        }
    }
}
