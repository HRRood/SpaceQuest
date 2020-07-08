package quest;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;

/**
 *  Main Class.
 *  the class that initializes everything for starting up the application/Game.
 */
public class Main extends Application {

    public static final String RESOURCE_PATH = "src/quest/Resources/";
    public static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    public static final int STAGE_WIDTH = (int) SCREEN_WIDTH;
    public static final int STAGE_HEIGHT = (int) SCREEN_HEIGHT;
    public static final int BUTTON_WIDTH = (int) (SCREEN_WIDTH * .2);
    public static final int BUTTON_HEIGHT = (int) (SCREEN_HEIGHT * .1);
    public static final Font FONT_130 = Font.loadFont(Main.class.getResource("Resources/pixel.ttf").toExternalForm(), 130);
    public static final Font FONT_40 = Font.loadFont(Main.class.getResource("Resources/pixel.ttf").toExternalForm(), 40);
    public static final Font FONT_20 = Font.loadFont(Main.class.getResource("Resources/pixel.ttf").toExternalForm(), 20);

    private Stage stage;

    public Menu menu;
    public GameOptions game_options;
    public Game game;

    /**
     * Start.
     * The JavaFX start class that initialize JavaFX.
     * this class also initializes Scenes that will be used.
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Space Quest");
        this.stage.setMaximized(true);
        this.stage.show();

        this.menu = new Menu(this);
        this.game_options = new GameOptions(this);

        this.gotoMenu();
    }

    /**
     * Stop.
     * The JavaFx stop class that terminates JavaFX.
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        this.stage.close();
    }

    /**
     * gotoMenu.
     * The function that sets the Menu scene in the Stage.
     *
     */
    public void gotoMenu() {
        this.stage.setScene(this.menu.getScene());
    }

    /**
     * gotoGameOptions
     * The function that sets the options for the game in the stage.
     *
     */
    public void gotoGameOptions() {
        this.stage.setScene(this.game_options.getScene());
    }

    /**
     * gotoGame.
     * The Function that initializes the game and sets it in the stage.
     *
     */
    public void gotoGame() {
        this.game = new Game(this, this.game_options);
        this.stage.setScene(this.game.getScene());
    }

    /**
     * main.
     * the main function that launches JavaFX start function.
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * FullResourcePath.
     * a String function that return the path to the resource map.
     *
     * @param file
     * @return
     */
    public static String FullResourcePath(String file) {
        return new File(Main.RESOURCE_PATH + file).toURI().toString();
    }

}
