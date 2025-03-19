package org.fdankic.liveoddsservice.core.service.feed;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessageCode;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.springframework.context.annotation.Bean;
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

    public void run() {
        System.out.println("FeedService initialized, reading messages ...");
        this.startFeed();
    }

    public void processLiveOddsMessage(LiveOddsMessage message) {
        feedMessages.add(message);
    }

    // Feed actions
    private void startFeed() {
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
                case LiveOddsMessageCode.MATCH_START:
                    scoreboardDAO.saveScoreboardMatch(message.toJson());
                    break;
                case LiveOddsMessageCode.MATCH_SCORE_UPDATE:
                    scoreboardDAO.updateScoreboardMatch(message.toJson());
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
        this.feedRunning = false;
        this.cleanFeed();
        System.out.println("FeedService closed.");
    }

    public void cleanFeed() {
        this.feedMessages = new CopyOnWriteArrayList<>();
    }
}
