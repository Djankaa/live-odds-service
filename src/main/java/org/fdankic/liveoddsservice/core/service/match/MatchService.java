package org.fdankic.liveoddsservice.core.service.match;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.fdankic.liveoddsservice.core.service.feed.FeedService;
import org.fdankic.liveoddsservice.core.service.liveodds.*;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.fdankic.liveoddsservice.core.domain.Match;
import org.fdankic.liveoddsservice.core.domain.MatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final ScoreboardDAO scoreboardDAO;

    @Setter
    @Autowired
    private FeedService feedService;

    public Match startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(Match.generateMatchId(), homeTeam, awayTeam);

        LiveOddsMessage startMessage = new MatchStartMessage(match.getId(), match.getHomeTeam(), match.getAwayTeam());
        feedService.processLiveOddsMessage(startMessage);

        return match;
    }

    public void updateMatch(String matchId, String team) {
        Match match = this.getMatch(matchId);

        if (!match.getStatus().equals(MatchStatus.MATCH_STATUS_IN_PROGRESS)) {
            System.out.println("Match status is " + match.getStatus() + " , for update it has to be in progress!");
            return;
        }

        if (team.equals(match.getHomeTeam())) {
            match.setHomeScore(match.getHomeScore() + 1);
        } else if (team.equals(match.getAwayTeam())) {
            match.setAwayScore(match.getAwayScore() + 1);
        }

        LiveOddsMessage scoreUpdateMessage = new MatchScoreUpdateMessage(match.getId(), match.getHomeScore(), match.getAwayScore());
        feedService.processLiveOddsMessage(scoreUpdateMessage);
    }

    public void finishMatch(String matchId) {
        Match match = this.getMatch(matchId);

        if (match.getStatus().equals(MatchStatus.MATCH_STATUS_FINISHED)) {
            System.out.println("Match already finished");
            return;
        }

        match.setStatus(MatchStatus.MATCH_STATUS_FINISHED);

        LiveOddsMessage finishMessage = new MatchFinishMessage(matchId);
        feedService.processLiveOddsMessage(finishMessage);
    }

    public void updateMatchDuration(String matchId) {
        Match match = this.getMatch(matchId);

        if (!match.getStatus().equals(MatchStatus.MATCH_STATUS_IN_PROGRESS)) {
            System.out.println("Match status is " + match.getStatus() + " , for update it has to be in progress!");
            return;
        }

        LiveOddsMessage aliveMessage = new MatchAliveMessage(match.getId());
        feedService.processLiveOddsMessage(aliveMessage);
    }

    public Match getMatch(String matchId) {
        return scoreboardDAO.getByMatchId(matchId);
    }
}
