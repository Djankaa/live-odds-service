package org.fdankic.liveoddsservice.adapters.simulator;

import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.match.MatchService;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public class MatchSimulator {
    private final MatchService matchService;

    public void startSimulation() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MatchSimulatorEvent matchEvent1 = new MatchSimulatorEvent( matchService,"Croatia", "France");
        matchEvent1.start();

        MatchSimulatorEvent matchEvent2 = new MatchSimulatorEvent( matchService,"Spain", "Italy");
        matchEvent2.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MatchSimulatorEvent matchEvent3 = new MatchSimulatorEvent(matchService,"Denmark", "England");
        matchEvent3.start();
    }
}
