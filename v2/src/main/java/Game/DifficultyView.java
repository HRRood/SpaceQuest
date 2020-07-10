package main.java.Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.Quest.Quest;

public class DifficultyView {

    private final Scene scene;

    public DifficultyView(Game game) {
        this.scene = this.render(game);
    }

    public Scene render(Game game) {
        Text title = game.objectBuilder.text("Quest Difficulty", FontSize.LARGE);

        VBox menuVBox = new VBox(title);
        menuVBox.setSpacing(15);
        menuVBox.setAlignment(Pos.CENTER);

        for (Difficulty difficulty : Difficulty.values()) {
            Button button = game.objectBuilder.button(difficulty.toString(), FontSize.MEDIUM, event -> {
                game.setQuestDifficulty(difficulty);
            });
            menuVBox.getChildren().add(button);
        }

        return new Scene(menuVBox, game.stage.getWidth(), game.stage.getHeight(), Color.DARKGRAY);
    }

    public Scene getScene() {
        return this.scene;
    }

}
