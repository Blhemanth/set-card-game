package com.example.game.service;

import com.example.game.model.GameSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameManager {

    private Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    // Create new game session
    public GameSession createSession() {
        String sessionId = UUID.randomUUID().toString().substring(0, 6);
        GameSession session = new GameSession(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    // Get session by ID
    public GameSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    // Check if session exists
    public boolean sessionExists(String sessionId) {
        return sessions.containsKey(sessionId);
    }
}