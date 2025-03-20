package org.fdankic.liveoddsservice.domain;

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
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Match(String id, String homeTeam, String awayTeam, int homeScore, int awayScore, String status, int duration) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.duration = duration;

        this.totalScore = homeScore + awayScore;
    }

    public static String generateMatchId() {
        return UUID.randomUUID().toString();
    }
}
