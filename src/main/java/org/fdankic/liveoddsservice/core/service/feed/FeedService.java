package org.fdankic.liveoddsservice.core.service.feed;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessageCode;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FeedService extends Thread {
    @Getter
    private List<LiveOddsMessage> feedMessages = new CopyOnWriteArrayList<>();

    private final ScoreboardDAO scoreboardDAO;
    private boolean feedRunning = true;

    @PostConstruct
    public void startFeed() {
        System.out.println("FeedService initialized, reading messages ...");
        this.start();
    }

    public void processLiveOddsMessage(LiveOddsMessage message) {
        feedMessages.add(message);
    }

    @Override
    public void run() {
        while(feedRunning) {
            if (!feedMessages.isEmpty()) {
                this.processFeedMessage();
            } else {
                System.out.println("No feed messages received");
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("FeedService interrupted" + e.getMessage());
            }
        }
    }

    private void processFeedMessage() {
        feedMessages.removeIf(message -> {
            this.feedPrintMessage(message);

            switch(message.getMessageCode()) {
                case LiveOddsMessageCode.MATCH_ALIVE:
                    scoreboardDAO.updateScoreboardMatchDuration(message.toJson());
                    break;
                case LiveOddsMessageCode.MATCH_START:
                    scoreboardDAO.saveScoreboardMatch(message.toJson());
                    break;
                case LiveOddsMessageCode.MATCH_SCORE_UPDATE:
                    scoreboardDAO.updateScoreboardMatchScore(message.toJson());
                    break;
                case LiveOddsMessageCode.MATCH_FINISH:
                    scoreboardDAO.deleteScoreboardMatch(message.toJson());
                    break;
                default:
            }

            return true;
        });
    }

    public void feedPrintMessage(LiveOddsMessage message) {
        System.out.println(message.getMessageCode());
        System.out.println(message.toJson());
    }

    public void stopFeed() {
        // TODO Check is feedMessages empty
        this.feedRunning = false;
        this.cleanFeed();
        System.out.println("FeedService closed.");
    }

    public void cleanFeed() {
        this.feedMessages = new CopyOnWriteArrayList<>();
    }
}
