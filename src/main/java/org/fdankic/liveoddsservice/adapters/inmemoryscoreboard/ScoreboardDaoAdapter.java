package org.fdankic.liveoddsservice.adapters.inmemoryscoreboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fdankic.liveoddsservice.adapters.inmemoryscoreboard.entities.ScoreboardMatch;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.fdankic.liveoddsservice.domain.Match;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScoreboardDaoAdapter implements ScoreboardDAO {
    private Map<String, ScoreboardMatch> scoreBoard = new HashMap<>();

    @Override
    public void saveScoreboardMatch(String rawMatchData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreboardMatch scoreboardMatch = objectMapper.readValue(rawMatchData, ScoreboardMatch.class);

            scoreBoard.put(scoreboardMatch.getMatchId(), scoreboardMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScoreboardMatchScore(String rawMatchData) {
        try {
            // Getting new data
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreboardMatch newScoreboardMatch = objectMapper.readValue(rawMatchData, ScoreboardMatch.class);

            // Taking out existing scoreboard and updating its values.
            ScoreboardMatch scoreboardMatch = this.findByMatchId(newScoreboardMatch.getMatchId());
            scoreboardMatch.setHomeScore(newScoreboardMatch.getHomeScore());
            scoreboardMatch.setAwayScore(newScoreboardMatch.getAwayScore());
            scoreboardMatch.setTotalScore(scoreboardMatch.getHomeScore() + scoreboardMatch.getAwayScore());

            scoreBoard.put(scoreboardMatch.getMatchId(), scoreboardMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScoreboardMatchDuration(String rawMatchData) {
        try {
            // Getting new data
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreboardMatch newScoreboardMatch = objectMapper.readValue(rawMatchData, ScoreboardMatch.class);

            // Taking out existing scoreboard and updating its values.
            ScoreboardMatch scoreboardMatch = this.findByMatchId(newScoreboardMatch.getMatchId());
            scoreboardMatch.setDuration(scoreboardMatch.getDuration() + newScoreboardMatch.getDuration());

            scoreBoard.put(scoreboardMatch.getMatchId(), scoreboardMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteScoreboardMatch(String rawMatchData) {
        try {
            // Getting new incoming data
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreboardMatch newScoreboardMatch = objectMapper.readValue(rawMatchData, ScoreboardMatch.class);

            // Fetching existing data and overriding the status.
            ScoreboardMatch scoreboardMatch = this.findByMatchId(newScoreboardMatch.getMatchId());
            scoreboardMatch.setStatus(newScoreboardMatch.getStatus());

            scoreBoard.put(scoreboardMatch.getMatchId(), scoreboardMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Match> getAll() {
        return this.getAll(null);
    }

    @Override
    public List<Match> getAll(String matchStatus) {
        return scoreBoard.values()
                .stream()
                .filter(scoreboardMatch -> matchStatus == null || matchStatus.equals(scoreboardMatch.getStatus()))
                .map(scoreboardMatch -> new Match(
                        scoreboardMatch.getMatchId(),
                        scoreboardMatch.getHomeTeam(),
                        scoreboardMatch.getAwayTeam(),
                        scoreboardMatch.getHomeScore(),
                        scoreboardMatch.getAwayScore(),
                        scoreboardMatch.getStatus(),
                        scoreboardMatch.getDuration()
                )).toList();
    }

    public Match getByMatchId(String matchId) {
        ScoreboardMatch scoreboardMatch = this.findByMatchId(matchId);
        return new Match(
                scoreboardMatch.getMatchId(),
                scoreboardMatch.getHomeTeam(),
                scoreboardMatch.getAwayTeam(),
                scoreboardMatch.getHomeScore(),
                scoreboardMatch.getAwayScore(),
                scoreboardMatch.getStatus(),
                scoreboardMatch.getDuration()
        );
    }

    // private
    private ScoreboardMatch findByMatchId(String matchId) {
        return scoreBoard.get(matchId);
    }
}
