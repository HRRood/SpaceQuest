package main.java.Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.Quest.Quest;

public class HighScoreView {

    private final Game game;
    private final Scene scene;
    private VBox content;

    public HighScoreView(Game game) {
        this.scene = this.render(game);
        this.game = game;
    }

    public Scene render(Game game) {
        Text title = game.objectBuilder.text("High Scores", FontSize.LARGE);

        this.content = new VBox();
        this.content.setSpacing(15);
        this.content.setAlignment(Pos.CENTER);

        Button menuButton = game.objectBuilder.button("Menu", FontSize.MEDIUM, event -> {
            game.stage.setScene(game.menuView.getScene());
        });

        VBox menuVBox = new VBox(
                title,
                this.content,
                menuButton
        );
        menuVBox.setSpacing(15);
        menuVBox.setAlignment(Pos.CENTER);

        return new Scene(menuVBox, game.stage.getWidth(), game.stage.getHeight(), Color.DARKGRAY);
    }

    private void updateContent() {
        this.content.getChildren().clear();

        if (this.game.quests.size() == 0) {
            Button newQuestButton = this.game.objectBuilder.button("New Quest", FontSize.MEDIUM, event -> {
                this.game.askQuestDifficulty();
            });
            this.content.getChildren().add(newQuestButton);
        } else {
            for (Quest quest : this.game.quests) {
                String str = String.format("%s\t%s\t%d",
                        quest.getPlayerName(),
                        quest.getDifficulty(),
                        quest.score
                );
                Text row = this.game.objectBuilder.text(str, FontSize.MEDIUM);
                this.content.getChildren().add(row);
            }
        }
    }

    public Scene getScene() {
        this.updateContent();

        return this.scene;
    }

}
