package main.java.Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PlayerNameView {

    private final Scene scene;
    private TextField nameField;

    public PlayerNameView(Game game) {
        this.scene = this.render(game);
    }

    public Scene render(Game game) {
        Text title = game.objectBuilder.text("Player Info", FontSize.LARGE);

        this.nameField = game.objectBuilder.textField(FontSize.MEDIUM);
        this.nameField.setAlignment(Pos.CENTER);
        this.nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.nameField.getText().length() > 12) {
                this.nameField.setText(this.nameField.getText().substring(0, 12));
            }
        });
        this.nameField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                if (this.nameField.getText().length() > 0) {
                    game.setQuestPlayerName(this.nameField.getText());
                } else {
                    this.nameField.setPromptText("REQUIRED");
                }
            }
        });

        VBox menuVBox = new VBox(
                title,
                this.nameField
        );
        menuVBox.setSpacing(15);
        menuVBox.setAlignment(Pos.CENTER);

        return new Scene(menuVBox, game.stage.getWidth(), game.stage.getHeight(), Color.DARKGRAY);
    }

    public Scene getScene() {
        this.nameField.setText("");

        return this.scene;
    }

}
