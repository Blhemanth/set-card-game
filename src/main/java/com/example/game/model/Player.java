package com.example.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerId;
    private List<Card> cards = new ArrayList<>();
    private int score = 0;

    public Player(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}