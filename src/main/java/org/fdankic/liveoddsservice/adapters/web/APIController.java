package org.fdankic.liveoddsservice.adapters.web;


import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {
    private final ScoreboardService scoreboardService;

    @GetMapping("/scoreboard")
    public ResponseEntity<?> getScoreboardAPI() {
        return ResponseEntity.ok(scoreboardService.getScoreboard());
    }
}
