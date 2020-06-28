package quest;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;

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

    private Menu menu;
    private GameOptions game_options;

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

    @Override
    public void stop() throws Exception {
        this.stage.close();
    }

    public void gotoMenu() {
        this.stage.setScene(this.menu.getScene());
    }

    public void gotoGameOptions() {
        this.stage.setScene(this.game_options.getScene());
    }

    public void gotoGame() {
        this.stage.setScene(new Game(this, this.game_options).getScene());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static String FullResourcePath(String file) {
        return new File(Main.RESOURCE_PATH + file).toURI().toString();
    }

}
