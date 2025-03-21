package org.fdankic.liveoddsservice.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Match {
    private final String id;

    private String homeTeam;
    private String awayTeam;

    private int homeScore = 0;
    private int awayScore = 0;
    private int totalScore = 0;

    private String status = MatchStatus.MATCH_STATUS_IN_PROGRESS;

    private int duration = 0;

    public Match(String id, String homeTeam, String awayTeam) {
        this.id = id;
        this.homeTeam = this.validateName(homeTeam);
        this.awayTeam = this.validateName(awayTeam);
    }

    public Match(String id, String homeTeam, String awayTeam, int homeScore, int awayScore, String status, int duration) {
        this.id = id;
        this.homeTeam = this.validateName(homeTeam);
        this.awayTeam = this.validateName(awayTeam);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.duration = duration;
        this.totalScore = homeScore + awayScore;
    }

    public String validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Match name cannot be null or empty");
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    // Generating matchId
    public static String generateMatchId() {
        return UUID.randomUUID().toString();
    }
}
