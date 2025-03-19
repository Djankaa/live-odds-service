package org.fdankic.liveoddsservice;

import org.fdankic.liveoddsservice.core.service.feed.FeedService;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchFinishMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchScoreUpdateMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchStartMessage;
import org.fdankic.liveoddsservice.domain.Match;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LiveOddsServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(LiveOddsServiceApplication.class, args);
        FeedService feedService = context.getBean(FeedService.class);

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
