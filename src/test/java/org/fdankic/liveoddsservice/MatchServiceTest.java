package org.fdankic.liveoddsservice;

import org.fdankic.liveoddsservice.adapters.inmemoryscoreboard.ScoreboardDaoAdapter;
import org.fdankic.liveoddsservice.core.service.feed.FeedService;
import org.fdankic.liveoddsservice.core.service.match.MatchService;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.fdankic.liveoddsservice.core.domain.Match;
import org.fdankic.liveoddsservice.core.domain.MatchStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchServiceTest {
    private MatchService matchService;
    private ScoreboardDAO scoreboardAdapter;

    private Match match;

    @BeforeEach
    void setUp() {
        scoreboardAdapter = new ScoreboardDaoAdapter();
        matchService = new MatchService(scoreboardAdapter);
        matchService.setFeedService(new FeedService(scoreboardAdapter));

        String matchId = "fa447c0c-4eeb-4703-8482-fc56347bbe3d";
        String rawMatchData = "{\"matchId\":\"fa447c0c-4eeb-4703-8482-fc56347bbe3d\",\"homeTeam\":\"Croatia\",\"awayTeam\":\"France\",\"status\":\"in_progress\"}";
        scoreboardAdapter.saveScoreboardMatch(rawMatchData);
        match = matchService.getMatch(matchId);
    }

    @Test
    void matchService_startMatchTest() {
        String homeTeam = "Croatia";
        String awayTeam = "France";

        Match match = matchService.startMatch(homeTeam, awayTeam);

        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(MatchStatus.MATCH_STATUS_IN_PROGRESS, match.getStatus());
    }

    @Test
    void matchService_finishMatchTest() {
        matchService.finishMatch(match.getId());

        String rawMatchData = "{\"matchId\":\"fa447c0c-4eeb-4703-8482-fc56347bbe3d\",\"status\":\"finished\"}";
        scoreboardAdapter.deleteScoreboardMatch(rawMatchData);
        match = matchService.getMatch(match.getId());

        assertEquals(MatchStatus.MATCH_STATUS_FINISHED, match.getStatus());
    }

    @Test
    void matchService_updateMatchDurationTest() {
        matchService.finishMatch(match.getId());

        String rawMatchData = "{\"matchId\":\"fa447c0c-4eeb-4703-8482-fc56347bbe3d\", \"duration\":\"1\"}";
        scoreboardAdapter.updateScoreboardMatchDuration(rawMatchData);
        match = matchService.getMatch(match.getId());

        assertEquals(1, match.getDuration());
    }
}
