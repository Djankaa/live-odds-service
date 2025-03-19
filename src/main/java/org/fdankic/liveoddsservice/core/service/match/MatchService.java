package org.fdankic.liveoddsservice.core.service.match;

import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.feed.FeedService;
import org.fdankic.liveoddsservice.core.service.liveodds.LiveOddsMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchFinishMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchScoreUpdateMessage;
import org.fdankic.liveoddsservice.core.service.liveodds.MatchStartMessage;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardDAO;
import org.fdankic.liveoddsservice.domain.Match;
import org.fdankic.liveoddsservice.domain.MatchStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final FeedService feedService;
    private final ScoreboardDAO scoreboardDAO;

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

        LiveOddsMessage finishMessage = new MatchFinishMessage(matchId);
        feedService.processLiveOddsMessage(finishMessage);
    }

    private Match getMatch(String matchId) {
        return scoreboardDAO.getByMatchId(matchId);
    }
}
