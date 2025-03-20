package org.fdankic.liveoddsservice.adapters.simulator;

import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.match.MatchService;
import org.fdankic.liveoddsservice.domain.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class MatchSimulatorEvent extends Thread {
    private final MatchService matchService;
    private final MatchSimulatorConfig matchSimulatorConfig;
    private final String homeTeam;
    private final String awayTeam;

    public void run() {
        this.generate();
    }

    public void generate() {
        Match match = matchService.startMatch(awayTeam, homeTeam);

        System.out.println("match duration " + matchSimulatorConfig.getMatchDuration());
        System.out.println("match segment every " + matchSimulatorConfig.getMatchSegments());

        long startTime = System.currentTimeMillis();
        long duration = matchSimulatorConfig.getMatchDuration() * 1000L;

        while (System.currentTimeMillis() - startTime < duration) {
            try {
                Thread.sleep(matchSimulatorConfig.getMatchSegments() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            matchService.updateMatchDuration(match.getId());

            String goal = this.goalChance(match.getHomeTeam(), match.getAwayTeam());
            if (!goal.isEmpty()) {
                matchService.updateMatch(match.getId(), goal);
            }
        }

        matchService.finishMatch(match.getId());
    }

    private String goalChance(String homeTeam, String awayTeam) {
        int random = ThreadLocalRandom.current().nextInt(1, 101);

        if (random <= 4) {
            return homeTeam;
        } else if (random >= 96) {
            return awayTeam;
        }

        return "";
    }
}
