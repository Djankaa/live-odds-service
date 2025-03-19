package org.fdankic.liveoddsservice.adapters.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/scoreboard")
    public String getScoreboard(){
        return "scoreboard.html";
    }
}
