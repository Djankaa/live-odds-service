package org.fdankic.liveoddsservice.adapters.web;


import lombok.RequiredArgsConstructor;
import org.fdankic.liveoddsservice.core.service.scoreboard.ScoreboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scoreboard")
@RequiredArgsConstructor
public class HomeController {
    private final ScoreboardService scoreboardService;

    @GetMapping
    public ResponseEntity<?> getScoreboardAPI() {
        return ResponseEntity.ok(scoreboardService.getScoreboard());
    }

}
