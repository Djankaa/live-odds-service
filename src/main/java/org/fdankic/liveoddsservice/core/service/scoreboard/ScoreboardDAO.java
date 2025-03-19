package org.fdankic.liveoddsservice.core.service.scoreboard;

import org.fdankic.liveoddsservice.adapters.inmemoryscoreboard.entities.ScoreboardMatch;
import org.fdankic.liveoddsservice.domain.Match;

import java.util.List;

public interface ScoreboardDAO {
    void saveScoreboardMatch(String rawMatchData);
    void updateScoreboardMatch(String rawMatchData);
    void deleteScoreboardMatch(String rawMatchData);
    List<Match> getAll();
    List<Match> getAll(String matchStatus);
    Match getByMatchId(String matchId);
}
