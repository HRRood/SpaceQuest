package main.java.Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.Quest.Quest;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends Application {

    public Stage stage;
    public ObjectBuilder objectBuilder;
    public ArrayList<Quest> quests;

    public MenuView menuView;
    public HighScoreView highScoreView;
    public DifficultyView difficultyView;
    public PlayerNameView playerNameView;

    private Difficulty difficulty;

    public static void launch(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Space Quest 2");
        this.stage.setScene(this.temporaryEmptyScene());
        this.stage.setMaximized(true);
        this.stage.show();
        this.stage.setMinWidth(this.stage.getWidth());
        this.stage.setMinHeight(this.stage.getHeight());

        this.objectBuilder = new ObjectBuilder(this, Game.GetFile("font/pixel.ttf"));
        this.quests = new ArrayList<>();

        this.menuView = new MenuView(this);
        this.highScoreView = new HighScoreView(this);
        this.difficultyView = new DifficultyView(this);
        this.playerNameView = new PlayerNameView(this);

        this.stage.setScene(this.menuView.getScene());
    }

    @Override
    public void stop() {
        this.stage.close();
        System.exit(33); // https://youtu.be/43HCYSXZ9GI
    }

    public void askQuestDifficulty() {
        this.stage.setScene(this.difficultyView.getScene());
    }

    public void setQuestDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;

        this.stage.setScene(this.playerNameView.getScene());
    }

    public void setQuestPlayerName(String playerName) {
        Quest quest = new Quest(this, this.difficulty, playerName);

        this.stage.setScene(quest.render());
    }

    public void addQuest(Quest quest) {
        if (quest.triumph) {
            this.quests.add(quest);
        }

        this.stage.setScene(this.menuView.getScene());
    }

    private Scene temporaryEmptyScene() {
        return new Scene(
                new Pane(),
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight()
        );
    }

    public static File GetFile(String name) {
        return new File("./src/main/resources/" + name);
    }

    public static int RandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}
