package org.fdankic.liveoddsservice.adapters.simulator;

import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.match.MatchService;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public class MatchSimulator {
    private final MatchService matchService;
    private final MatchSimulatorConfig matchSimulatorConfig;

    public void startSimulation() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MatchSimulatorEvent matchEvent1 = new MatchSimulatorEvent(
                matchService,
                matchSimulatorConfig,
                "Croatia",
                "France");
        matchEvent1.start();

        MatchSimulatorEvent matchEvent2 = new MatchSimulatorEvent(
                matchService,
                matchSimulatorConfig,
                "Spain",
                "Italy");
        matchEvent2.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MatchSimulatorEvent matchEvent3 = new MatchSimulatorEvent(
                matchService,
                matchSimulatorConfig,
                "Denmark",
                "England");
        matchEvent3.start();

        MatchSimulatorEvent matchEvent4 = new MatchSimulatorEvent(
                matchService,
                matchSimulatorConfig,
                "Poland",
                "Germany");
        matchEvent4.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MatchSimulatorEvent matchEvent5 = new MatchSimulatorEvent(
                matchService,
                matchSimulatorConfig,
                "Portugal",
                "Switzerland");
        matchEvent5.start();
    }
}
