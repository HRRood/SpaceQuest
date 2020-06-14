package quest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final String[] MENU_ITEM = {"start", "Options"};

    public static final int TILE_SIZE = 50;
    private static final int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();

    private static final int X_TILES = 15;
    private static final int Y_TILES = 15;

    private Stage game_stage;
    private Stage primaryStage;
    private Scene game_scene;
    private Scene menu_scene;

    private Pane game;
    private StackPane menu;
    private Tile[][] grid = new Tile[X_TILES][Y_TILES];

    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        menu_scene = new Scene(createMenu());
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Space Quest");
        this.primaryStage.setScene(menu_scene);
        this.primaryStage.show();
    }

    private Parent createGame() {
        game = new Pane();
        game.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        game.relocate(SCREEN_WIDTH * 0.3, SCREEN_HEIGHT * 0.1);

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

        user = new User(new Image (new File("src/quest/spaceship.png").toURI().toString()), grid[0][0], "up");
        grid[0][0].setUser(user);
        updateGame();
        return game;
    }

    private void movePlayer(String move_to) {
        Tile user_tile = user.getTile();
        List<Tile> neighbours = user_tile.getNeighbours();
        switch (move_to) {
            case "up": {
                if (neighbours.get(0).isAvailable()) {
                    user_tile.setUser(null);
                    neighbours.get(0).setUser(user);
                    user.setTile(neighbours.get(0));
                }
                break;
            }
            case "left": {
                if (neighbours.get(1).isAvailable()) {
                    user_tile.setUser(null);
                    neighbours.get(1).setUser(user);
                    user.setTile(neighbours.get(1));
                }
                break;
            }
            case "right": {
                if (neighbours.get(2).isAvailable()) {
                    user_tile.setUser(null);
                    neighbours.get(2).setUser(user);
                    user.setTile(neighbours.get(2));
                }
                break;
            }
            case "down": {
                if (neighbours.get(3).isAvailable()) {
                    user_tile.setUser(null);
                    neighbours.get(3).setUser(user);
                    user.setTile(neighbours.get(3));
                }
                break;
            }
        }
        updateGame();
    }

    private void addHandlers() {
        game_scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP: {
                    user.setDirection("up");
                    movePlayer("up");
                    break;
                }
                case DOWN: {
                    user.setDirection("down");
                    movePlayer("down");
                    break;
                }
                case LEFT: {
                    user.setDirection("left");
                    movePlayer("left");
                    break;
                }
                case RIGHT: {
                    user.setDirection("right");
                    movePlayer("right");
                    break;
                }
            }
        });
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

    private void updateGame() {
        if (!game.getChildren().isEmpty()) {
            game.getChildren().clear();
        }
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                game.getChildren().add(grid[x][y].getPane());
            }
        }
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


    public static void main(String[] args) {
        launch(args);
    }
}
