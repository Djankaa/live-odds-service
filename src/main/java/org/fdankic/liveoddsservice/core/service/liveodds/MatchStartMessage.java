package org.fdankic.liveoddsservice.core.service.liveodds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.domain.Match;

@RequiredArgsConstructor
public class MatchStartMessage implements LiveOddsMessage {
    @JsonProperty
    final String messageCode = "MATCH_START";

    @JsonProperty
    private final String matchId;

    @JsonProperty
    private final String homeTeam;

    @JsonProperty
    private final String awayTeam;

    @JsonProperty
    private final String status = "in_progress";

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
