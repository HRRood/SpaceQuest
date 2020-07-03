package quest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Menu Class.
 * The menu for the game and the main return point for the application.
 * The user can from this class switch scenes between options and the game.
 */
public class Menu {

    private Scene scene;

    /**
     * Menu class constructor.
     * calls the construction function.
     *
     * @param main
     */
    public Menu(Main main) {
        this.constructScene(main);
    }

    /**
     * GetScene.
     * returns the scene of the class.
     *
     * @return
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * ConstructScene.
     * initializes the scene for the menu.
     *
     * @param main
     */
    private void constructScene(Main main) {
        Text text = new Text("Space Quest");
        text.setFont(Main.FONT_130);

        Button start_button = newButton("Start Game", e -> main.gotoGame());
        Button options_button = newButton("Game Options", e -> main.gotoGameOptions());
        Button exit_button = newButton("Exit", e -> {
            try {
                main.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        VBox menu_buttons = new VBox(
                text,
                start_button,
                options_button,
                exit_button
        );
        menu_buttons.setSpacing(10);
        menu_buttons.setAlignment(Pos.CENTER);

        this.scene = new Scene(menu_buttons, Main.STAGE_WIDTH, Main.STAGE_HEIGHT, Color.DARKGRAY);
    }

    /**
     * newButton.
     * Makes the button for the menu with handlers.
     *
     * @param text
     * @param event_handler
     * @return
     */
    private Button newButton(String text, EventHandler<ActionEvent> event_handler) {
        Button button = new Button(text);
        button.setFont(Main.FONT_20);
        button.setMinSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);

        if (event_handler != null) {
            button.setOnAction(event_handler);
        }

        return button;
    }

}
