package com.example.game.controller;

import com.example.game.model.GameSession;
import com.example.game.model.Player;
import com.example.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    // Create session
    @GetMapping("/create")
    public GameSession createSession() {
        return gameService.createSession();
    }

    // Join session
    @GetMapping("/join")
    public String join(@RequestParam String sessionId) {
        return gameService.joinSession(sessionId);
    }

    // Start game
    @GetMapping("/start")
    public String start(@RequestParam String sessionId) {
        gameService.startGame(sessionId);
        return "Game Started!";
    }

    // Pass card
    @GetMapping("/pass")
    public String passCard(
            @RequestParam String sessionId,
            @RequestParam String playerId,
            @RequestParam String cardValue) {

        gameService.passCard(sessionId, playerId, cardValue);
        return "Card Passed!";
    }

    // Game state (ONLY ONE METHOD)
    @GetMapping("/state")
    public GameSession getState(@RequestParam String sessionId) {
        return gameService.getGameState(sessionId);
    }
    @GetMapping("/set")
public String callSet(
        @RequestParam String sessionId,
        @RequestParam String playerId) {

    return gameService.checkSet(sessionId, playerId);
}
@GetMapping("/winner")
public Player winner(@RequestParam String sessionId) {
    return gameService.getWinner(sessionId);
}
}