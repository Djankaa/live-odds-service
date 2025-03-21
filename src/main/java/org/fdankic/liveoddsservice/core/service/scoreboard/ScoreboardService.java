package org.fdankic.liveoddsservice.core.service.scoreboard;

import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.domain.Match;
import org.fdankic.liveoddsservice.core.domain.MatchStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScoreboardService {
    private final ScoreboardDAO scoreboardDAO;

    public List<Match> getScoreboard() {
        List<Match> matches = new ArrayList<>(
            scoreboardDAO.getAll(MatchStatus.MATCH_STATUS_IN_PROGRESS)
        );

        matches.sort(
            Comparator
                .comparing(Match::getTotalScore)
                .reversed()
                .thenComparing(Match::getDuration)
        );

        return matches;
    }
}
