package com.example.game.service;

import com.example.game.model.Card;
import com.example.game.model.GameSession;
import com.example.game.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    private GameManager gameManager;

    // =========================
    // CREATE SESSION
    // =========================
    public GameSession createSession() {
        return gameManager.createSession();
    }

    // =========================
    // JOIN SESSION
    // =========================
    public String joinSession(String sessionId) {
        GameSession session = gameManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Session not found");
        }

        // Limit players
        if (session.getPlayers().size() >= 4) {
            throw new RuntimeException("Max 4 players allowed");
        }

        String playerId = "P" + (session.getPlayers().size() + 1);
        Player player = new Player(playerId);

        session.getPlayers().put(playerId, player);
        session.getTurnOrder().add(playerId); // IMPORTANT

        return playerId;
    }

    // =========================
    // START GAME
    // =========================
    public void startGame(String sessionId) {

    GameSession session = gameManager.getSession(sessionId);

    if (session == null) {
        throw new RuntimeException("Session not found");
    }

    if (session.getPlayers().isEmpty()) {
        throw new RuntimeException("No players joined");
    }

    // Start game
    session.setGameStarted(true);

    // Prepare turn order
    session.getTurnOrder().clear();
    for (String playerId : session.getPlayers().keySet()) {
        session.getTurnOrder().add(playerId);
    }

    // Set first turn
    session.setCurrentTurn(session.getTurnOrder().peek());

    // 🔥 CLEAR OLD CARDS (IMPORTANT)
    for (Player player : session.getPlayers().values()) {
        player.getCards().clear();
    }

    // 🔥 GENERATE VALUES BASED ON PLAYER COUNT
    int playerCount = session.getPlayers().size();

    List<String> allowedValues = new ArrayList<>();
    for (int i = 0; i < playerCount; i++) {
        allowedValues.add(String.valueOf((char) ('A' + i)));
    }

    // 🔥 DISTRIBUTE CARDS (EACH PLAYER UNIQUE)
    List<Player> players = new ArrayList<>(session.getPlayers().values());

    for (int i = 0; i < players.size(); i++) {

        Player player = players.get(i);
        String value = allowedValues.get(i);

        for (int j = 0; j < 4; j++) {
            player.getCards().add(new Card(value));
        }
    }
}

    // =========================
    // PASS CARD
    // =========================
    public void passCard(String sessionId, String playerId, String cardValue) {

        GameSession session = gameManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Session not found");
        }

        if (!session.isGameStarted()) {
            throw new RuntimeException("Game is already over");
        }

        if (session.getCurrentTurn() == null || !session.getCurrentTurn().equals(playerId)) {
            throw new RuntimeException("Not your turn");
        }

        Player player = session.getPlayers().get(playerId);

        if (player == null) {
            throw new RuntimeException("Player not found");
        }

        // Find card
        Card cardToPass = null;
        for (Card c : player.getCards()) {
            if (c.getValue().equals(cardValue)) {
                cardToPass = c;
                break;
            }
        }

        if (cardToPass == null) {
            throw new RuntimeException("Card not found");
        }

        // Remove card
        player.getCards().remove(cardToPass);

        // Rotate turn
        String current = session.getTurnOrder().poll();
        session.getTurnOrder().add(current);

        String nextPlayerId = session.getTurnOrder().peek();

        // Give card
        session.getPlayers().get(nextPlayerId).getCards().add(cardToPass);

        session.setCurrentTurn(nextPlayerId);
    }

    // =========================
    // GAME STATE
    // =========================
    public GameSession getGameState(String sessionId) {
        GameSession session = gameManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Session not found");
        }

        return session;
    }

    // =========================
    // SET VALIDATION
    // =========================
    public String checkSet(String sessionId, String playerId) {

        GameSession session = gameManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Session not found");
        }

        if (!session.isGameStarted()) {
            throw new RuntimeException("Game is already over");
        }

        Player player = session.getPlayers().get(playerId);

        if (player == null) {
            throw new RuntimeException("Player not found");
        }

        boolean valid = player.getCards()
                .stream()
                .map(Card::getValue)
                .distinct()
                .count() == 1;

        if (valid) {
            session.setSetCount(session.getSetCount() + 1);

            int setNumber = session.getSetCount();
            int score = 1000 - (setNumber - 1) * 100;

            player.setScore(player.getScore() + score);

            if (session.getSetCount() >= 5) {
                session.setGameStarted(false);
                return "Valid SET! +" + score + " points | GAME OVER";
            }

            return "Valid SET! +" + score + " points";
        } else {
            player.setScore(player.getScore() - 200);
            return "Invalid SET! -200 points";
        }
    }

    // =========================
    // WINNER
    // =========================
    public Player getWinner(String sessionId) {

        GameSession session = gameManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Session not found");
        }

        Player winner = null;
        int maxScore = Integer.MIN_VALUE;

        for (Player player : session.getPlayers().values()) {
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                winner = player;
            }
        }

        return winner;
    }
}