package quest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {


    String resources = "src/quest/Resources/";

    public static final int TILE_SIZE = 60;

    private static final int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();

    private static final int X_TILES = 15;
    private static final int Y_TILES = 15;

    private Stage primaryStage;
    private Scene game_scene;
    private Scene menu_scene;

    private Pane game;
    private StackPane menu;
    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private Label score_text;


    private User user;

    private Integer score = -1;

    private Comet[] comets = new Comet[5];

    @Override
    public void start(Stage primaryStage) throws Exception{
        menu_scene = new Scene(createMenu());
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Space Quest");
        this.primaryStage.setScene(menu_scene);
        this.primaryStage.show();
    }

    private Parent createMenu () {
        menu = new StackPane();
        menu.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));

        Button start = new Button("Start Game");
        start.setMinSize(SCREEN_WIDTH * 0.2, SCREEN_HEIGHT * 0.1);
        start.setOnAction((event) -> {
            game_scene = new Scene(createGame());
            addHandlers();
            primaryStage.setScene(game_scene);
        });

        Button options = new Button("Game Options");
        options.setMinSize(SCREEN_WIDTH * 0.2, SCREEN_HEIGHT * 0.1);
        vbButtons.getChildren().addAll(start, options);

        menu.getChildren().add(vbButtons);
        vbButtons.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-background-color: darkgray;");;
        return menu;
    }

    private Parent createGame() {

        //Creating the screen.
        StackPane root = new StackPane();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        game = new Pane();
        game.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        //creating the score.
        String player_score = "Score: " + score;
        score_text = new Label(player_score);
        score_text.setFont(new Font(38));
        StackPane.setAlignment(score_text, Pos.TOP_CENTER);

        //background image.
        Image tile_background = new Image (new File(resources + "space-background.png").toURI().toString());


        //creating tiles for board.
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y);
                grid[x][y] = tile;
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                grid[x][y].setNeighbours(getNeighbours(grid[x][y]));
            }
        }


        //creating player.
        user = new User(new Image (new File(resources + "spaceship.png").toURI().toString()), grid[0][0], "up");
        grid[0][0].setObject(user);

        //creating Comets.
        for(int i = 0; i < comets.length; i++)
        {
            int posX = getRandom(1,X_TILES -1);
            int posY = getRandom(1,Y_TILES -1);
            Comet comet = new Comet(new Image (new File(resources + "Meteorites.png").toURI().toString()), grid[posX][posY]);
            grid[posX][posY].setObject(comet);
            comets[i] = comet;
            System.out.println("Comet: " + i + " - pos: " + posX + ":" + posY);
        }

        //updating game.
        updateGame();
        return game;
    }

    private void addHandlers() {
        //set time/score.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        score++;
                        updateGame();
                        LateUpdate();
                    }
                });
            }
        }, 0, 1000);

        //get player input.
        game_scene.setOnKeyPressed(event -> {
            user.handleKeyPressed(event.getCode());
            updateGame();
        });
    }

    private List<Tile> getNeighbours (Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        Tile emptyTile = new Tile(-1, -1);

        int[] points = new int[] {
                0, -1, -1, 0, 1, 0, 0, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getPosition_x() + dx;
            int newY = tile.getPosition_y() + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            } else {
                neighbors.add(emptyTile);
            }
        }

        return neighbors;
    }

    private int getRandom(int min, int max)
    {
        int temp = (int)(Math.random() * (max - min + 1) + min);
        return temp;
    }

    //Updating all tiles & Game objects, also renders.
    public void updateGame()  {
        //clean board.
        if (!game.getChildren().isEmpty()) {
            game.getChildren().clear();
        }
        //update board.
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                game.getChildren().add(grid[x][y].getPane());
            }
        }

        //board location
        int game_size = TILE_SIZE * X_TILES;
        game.setTranslateX((SCREEN_WIDTH * 0.5) - (game_size * 0.5));
        game.setTranslateY((SCREEN_HEIGHT * 0.5) - (game_size * 0.5));




        //updating score.
        String text = "Score: " + score;
        score_text.setText(text);
    }

    public void LateUpdate()
    {
        //update comets.
        for (Comet c :  comets) {
            c.Update();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
