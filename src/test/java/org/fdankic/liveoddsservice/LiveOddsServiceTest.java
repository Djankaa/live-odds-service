package org.fdankic.liveoddsservice;

import org.fdankic.liveoddsservice.core.service.feed.FeedService;
import org.fdankic.liveoddsservice.core.service.liveodds.*;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.fdankic.liveoddsservice.domain.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiveOddsServiceTest {

    private FeedService feedService;
    private ScoreboardDAO scoreboardDAO;

    @BeforeEach
    void setUp() {
        feedService = new FeedService(scoreboardDAO);
        feedService.cleanFeed();
    }

    @Test
    void processLiveOddsMessage_MatchStartMessage() {
        Match match = new Match(
                Match.generateMatchId(),
                "Croatia",
                "France"
        );
        LiveOddsMessage message = new MatchStartMessage(match.getId(), match.getHomeTeam(), match.getAwayTeam());

        feedService.processLiveOddsMessage(message);

        List<LiveOddsMessage> feedServiceMessages = feedService.getFeedMessages();
        assertEquals(1, feedServiceMessages.size());
        assertEquals(message, feedServiceMessages.get(0));
        assertEquals(LiveOddsMessageCode.MATCH_START, message.getMessageCode());
    }

    @Test
    void processLiveOddsMessage_MatchScoreUpdateMessage() {
        Match match = new Match(
                Match.generateMatchId(),
                "Croatia",
                "France"
        );
        LiveOddsMessage message = new MatchScoreUpdateMessage(match.getId(), 0, 1);

        feedService.processLiveOddsMessage(message);

        List<LiveOddsMessage> feedServiceMessages = feedService.getFeedMessages();
        assertEquals(1, feedServiceMessages.size());
        assertEquals(message, feedServiceMessages.get(0));
        assertEquals(LiveOddsMessageCode.MATCH_SCORE_UPDATE, message.getMessageCode());
    }

    @Test
    void processLiveOddsMessage_MatchFinishMessage() {
        Match match = new Match(
                Match.generateMatchId(),
                "Croatia",
                "France"
        );
        LiveOddsMessage message = new MatchFinishMessage(match.getId());

        feedService.processLiveOddsMessage(message);

        List<LiveOddsMessage> feedServiceMessages = feedService.getFeedMessages();
        assertEquals(1, feedServiceMessages.size());
        assertEquals(message, feedServiceMessages.get(0));
        assertEquals(LiveOddsMessageCode.MATCH_FINISH, message.getMessageCode());
    }

    @Test
    void processLiveOddsMessage_MatchAliveMessage() {
        Match match = new Match(
                Match.generateMatchId(),
                "Croatia",
                "France"
        );
        LiveOddsMessage message = new MatchAliveMessage(match.getId(), 1);

        feedService.processLiveOddsMessage(message);

        List<LiveOddsMessage> feedServiceMessages = feedService.getFeedMessages();
        assertEquals(1, feedServiceMessages.size());
        assertEquals(message, feedServiceMessages.get(0));
        assertEquals(LiveOddsMessageCode.MATCH_ALIVE, message.getMessageCode());
    }
}
