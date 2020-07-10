package main.java.Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuView {

    private final Scene scene;

    public MenuView(Game game) {
        this.scene = this.render(game);
    }

    public Scene render(Game game) {
        Text title = game.objectBuilder.text("Space Quest", FontSize.LARGE);

        Button newQuestButton = game.objectBuilder.button("New Quest", FontSize.MEDIUM, event -> {
            game.askQuestDifficulty();
        });

        Button highScoresButton = game.objectBuilder.button("High scores", FontSize.MEDIUM, event -> {
            game.stage.setScene(game.highScoreView.getScene());
        });

        Button exitButton = game.objectBuilder.button("Exit", FontSize.MEDIUM, event -> {
            game.stop();
        });

        VBox menuVBox = new VBox(
                title,
                newQuestButton,
                highScoresButton,
                exitButton
        );
        menuVBox.setSpacing(15);
        menuVBox.setAlignment(Pos.CENTER);

        return new Scene(menuVBox, game.stage.getWidth(), game.stage.getHeight(), Color.DARKGRAY);
    }

    public Scene getScene() {
        return this.scene;
    }

}
