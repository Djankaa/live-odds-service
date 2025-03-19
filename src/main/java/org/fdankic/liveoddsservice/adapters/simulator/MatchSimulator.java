package org.fdankic.liveoddsservice.adapters.simulator;

import jakarta.annotation.PostConstruct;
import org.fdankic.liveoddsservice.core.service.feed.FeedService;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchFinishMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchScoreUpdateMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchStartMessage;
import org.fdankic.liveoddsservice.domain.Match;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

@Controller
public class MatchSimulator {
    private final FeedService feedService;

    public MatchSimulator(FeedService feedService) {
        this.feedService = feedService;
    }

    public void startSimulation() throws InterruptedException {
        feedService.start();

        Match match = new Match(Match.generateMatchId(),"Croatia", "France");
        LiveOddsMessage msg = new MatchStartMessage(match.getId(), match.getHomeTeam(), match.getAwayTeam());
        feedService.processLiveOddsMessage(msg);

        TimeUnit.SECONDS.sleep(3);

        Match match1 = new Match(Match.generateMatchId(),"Belgium", "Italy");
        LiveOddsMessage msg1 = new MatchStartMessage(match1.getId(), match1.getHomeTeam(), match1.getAwayTeam());
        feedService.processLiveOddsMessage(msg1);

        TimeUnit.SECONDS.sleep(2);
        LiveOddsMessage msgS = new MatchScoreUpdateMessage(match.getId(), 1, 0);
        feedService.processLiveOddsMessage(msgS);

        TimeUnit.SECONDS.sleep(5);

        LiveOddsMessage msg4 = new MatchFinishMessage(match.getId());
        feedService.processLiveOddsMessage(msg4);

        TimeUnit.SECONDS.sleep(6);
        feedService.stopFeed();
    }
}
