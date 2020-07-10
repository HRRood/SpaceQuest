package main.java.Quest;

import javafx.scene.Scene;
import main.java.Game.Difficulty;
import main.java.Game.Game;

public class Quest {

    public final Game game;
    private final Difficulty difficulty;
    private final String playerName;

    public Galaxy galaxy;

    public int score;
    public boolean triumph;
    public boolean defeat;

    public Quest(Game game, Difficulty difficulty, String playerName) {
        this.game = game;
        this.difficulty = difficulty;
        this.playerName = playerName;
        this.score = 0;

        this.galaxy = new Galaxy(this);
    }

    public Scene render() {
        return this.galaxy.getScene();
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public String getPlayerName() {
        return this.playerName;
    }

}
