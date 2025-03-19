package org.fdankic.liveoddsservice.adapters.inmemoryscoreboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardMatch {
    private String matchId;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private int totalScore;
    private String status;

    public ScoreboardMatch() {}
}
