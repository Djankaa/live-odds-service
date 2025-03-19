package org.fdankic.liveoddsservice.adapters.inmemoryscoreboard;

import com.fasterxml.jackson.core.JsonProcessingException;
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

            //scoreboardMatch.setTotalScore(scoreboardMatch.getHomeScore() + scoreboardMatch.getAwayScore());
            scoreBoard.put(scoreboardMatch.getMatchId(), scoreboardMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScoreboardMatch(String rawMatchData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreboardMatch scoreboardMatch = objectMapper.readValue(rawMatchData, ScoreboardMatch.class);

            //scoreboardMatch.setTotalScore(scoreboardMatch.getHomeScore() + scoreboardMatch.getAwayScore());
            scoreBoard.put(scoreboardMatch.getMatchId(), scoreboardMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteScoreboardMatch(String rawMatchData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreboardMatch scoreboardMatch = objectMapper.readValue(rawMatchData, ScoreboardMatch.class);

            scoreBoard.remove(scoreboardMatch.getMatchId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Match> getAll() {
        return scoreBoard.values()
                .stream()
                .map(scoreboardMatch -> new Match(
                        scoreboardMatch.getMatchId(),
                        scoreboardMatch.getHomeTeam(),
                        scoreboardMatch.getAwayTeam()
                )).toList();
    }
}
