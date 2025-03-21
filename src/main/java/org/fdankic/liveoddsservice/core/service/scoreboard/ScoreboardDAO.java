package org.fdankic.liveoddsservice.core.service.scoreboard;

import org.fdankic.liveoddsservice.core.domain.Match;

import java.util.List;

public interface ScoreboardDAO {
    void saveScoreboardMatch(String rawMatchData);
    void updateScoreboardMatchScore(String rawMatchData);
    void updateScoreboardMatchDuration(String rawMatchData);
    void deleteScoreboardMatch(String rawMatchData);
    List<Match> getAll();
    List<Match> getAll(String matchStatus);
    Match getByMatchId(String matchId);
}
