package org.fdankic.liveoddsservice.core.service.liveodds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.domain.Match;
import org.fdankic.liveoddsservice.domain.MatchStatus;

@RequiredArgsConstructor
public class MatchScoreUpdateMessage implements LiveOddsMessage {
    @JsonProperty
    final String messageCode = "MATCH_SCORE_UPDATE";

    @JsonProperty
    private final String matchId;

    @JsonProperty
    private final int homeScore;

    @JsonProperty
    private final int awayScore;

    @JsonProperty
    private final String status = MatchStatus.MATCH_STATUS_IN_PROGRESS;

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
