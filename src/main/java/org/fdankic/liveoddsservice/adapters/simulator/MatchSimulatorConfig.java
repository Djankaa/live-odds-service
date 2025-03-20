package org.fdankic.liveoddsservice.adapters.simulator;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MatchSimulatorConfig {
    @Value("${simulator.matchDuration}")
    private int matchDuration;

    private int matchSegments;

    private static final int SEGMENT_NUMBER = 90;

    @PostConstruct
    public void validateMatchSimulatorConfig() {
        if (matchDuration < SEGMENT_NUMBER) {
            throw new IllegalArgumentException("Match duration must be at least 90 seconds");
        }

        matchSegments = matchDuration / SEGMENT_NUMBER;
    }
}
