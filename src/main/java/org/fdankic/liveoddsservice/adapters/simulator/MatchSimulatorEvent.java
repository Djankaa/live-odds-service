package org.fdankic.liveoddsservice.adapters.simulator;

import org.fdankic.liveoddsservice.core.service.match.MatchService;
import org.fdankic.liveoddsservice.domain.Match;

import java.util.Random;

public class MatchSimulatorEvent extends Thread {
    private final MatchService matchService;

    private final String homeTeam;
    private final String awayTeam;

    public MatchSimulatorEvent(MatchService matchService, String homeTeam, String awayTeam) {
        this.matchService = matchService;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void run() {
        this.generate();
    }

    public void generate() {
        Match match = matchService.startMatch(awayTeam, homeTeam);

        long startTime = System.currentTimeMillis();
        long duration = 90 * 1000;

        while (System.currentTimeMillis() - startTime < duration) {
            try {
                Thread.sleep(this.nextGoalChance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String goal = this.goalChance(match.getHomeTeam(), match.getAwayTeam());
            if (!goal.isEmpty()) {
                matchService.updateMatch(match.getId(), goal);
            }
        }

        matchService.finishMatch(match.getId());
    }

    private int nextGoalChance() {
        int goalChanceIn = new Random().nextInt(25 - 5) + 5;
        return goalChanceIn * 1000;
    }

    private String goalChance(String homeTeam, String awayTeam) {
        int random = new Random().nextInt(100) + 1;

        if (random < 25) {
            return homeTeam;
        } else if (random > 75) {
            return awayTeam;
        }

        return "";
    }
}
