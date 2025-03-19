package org.fdankic.liveoddsservice.adapters.simulator;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatchSimulatorRunner {
    @Bean
    public ApplicationRunner startMatchSimulator(MatchSimulator matchSimulator) {
        return args -> matchSimulator.startSimulation();
    }
}
