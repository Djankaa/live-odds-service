package org.fdankic.liveoddsservice.core.service.scoreboard;

import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.domain.Match;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreboardService {
    private final ScoreboardDAO scoreboardDAO;

    public List<Match> getScoreboard() {
        return scoreboardDAO.getAll();
    }
}
