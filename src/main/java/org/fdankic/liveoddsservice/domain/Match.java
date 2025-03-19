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
    private String status = "init";

    public Match(String id, String homeTeam, String awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public static String generateMatchId() {
        return UUID.randomUUID().toString();
    }
}
