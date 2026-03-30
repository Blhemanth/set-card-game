package com.example.game.model;

import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class GameSession {

    private String sessionId;
    private Map<String, Player> players = new ConcurrentHashMap<>();
    private Queue<String> turnOrder = new LinkedList<>();
    private String currentTurn;
    private boolean gameStarted = false;
    private int setCount = 0;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Queue<String> getTurnOrder() {
        return turnOrder;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }
}