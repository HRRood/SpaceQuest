package main.java.Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;

public class ObjectBuilder {

    private final Game game;

    private final Font fontLarge;
    private final Font fontMedium;
    private final Font fontSmall;

    public ObjectBuilder(Game game, File fontFile) throws Exception {
        this.game = game;

        this.fontLarge = Font.loadFont(
                new FileInputStream(fontFile),
                this.game.stage.getHeight() * .1
        );
        this.fontMedium = Font.loadFont(
                new FileInputStream(fontFile),
                this.game.stage.getHeight() * .05
        );
        this.fontSmall = Font.loadFont(
                new FileInputStream(fontFile),
                this.game.stage.getHeight() * .025
        );
    }

    public Text text(String str, FontSize fontSize) {
        Text text = new Text(str);
        text.setFont(this.getFont(fontSize));

        return text;
    }

    public Button button(String str, FontSize fontSize, EventHandler<ActionEvent> eventHandler) {
        Button button = new javafx.scene.control.Button(str);
        button.setFont(this.getFont(fontSize));
        button.setMinSize((int) (this.game.stage.getWidth() * .2), (int) (this.game.stage.getHeight() * .1));

        if (eventHandler != null) {
            button.setOnAction(eventHandler);
        }

        return button;
    }

    public TextField textField(FontSize fontSize) {
        TextField textField = new TextField ();
        textField.setMinWidth((int) (this.game.stage.getWidth() * .3));
        textField.setMaxWidth((int) (this.game.stage.getWidth() * .3));
        textField.setFont(this.getFont(fontSize));

        return textField;
    }

    private Font getFont(FontSize fontSize) {
        switch (fontSize) {
            case LARGE:
                return this.fontLarge;
            case MEDIUM:
                return this.fontMedium;
            case SMALL:
            default:
                return this.fontSmall;
        }
    }

}
