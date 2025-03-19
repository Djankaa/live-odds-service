package org.fdankic.liveoddsservice.core.service.liveodds;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class MatchFinishMessage implements LiveOddsMessage {
    @JsonProperty
    final String messageCode = "MATCH_FINISH";

    @JsonProperty
    private final String matchId;

    @JsonProperty
    private final String status = "finished";

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
