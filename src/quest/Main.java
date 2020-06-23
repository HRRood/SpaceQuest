package quest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {

    String resources_path = "src/quest/Resources/";

    public static final int TILE_SIZE = 60;
    private static final int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight() ;

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

    private int comet_number = 5;
    private Comet[] comets = new Comet[comet_number];

    private int planet_count = 5;
    private Planet[] planets = new Planet[planet_count];

    private Wormhole wormhole;

    private Pane go_menu;

    public static boolean game_over = false;
    public static boolean game_won = false;

    public boolean all_planets_visited = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        menu_scene = new Scene(createMenu());
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Space Quest");
        this.primaryStage.setScene(menu_scene);
        this.primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        this.exitApplication();
    }

    //creates the menu.
    private Parent createMenu () {
        this.menu = new StackPane();
        this.menu.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        Button start_button = new Button("Start Game");
        start_button.setMinSize(SCREEN_WIDTH * 0.2, SCREEN_HEIGHT * 0.1);
        start_button.setOnAction(event -> {
            this.game_scene = new Scene(createGame());
            this.addHandlers();
            this.primaryStage.setScene(this.game_scene);
        });

        Button options_button = new Button("Game Options");
        options_button.setMinSize(SCREEN_WIDTH * 0.2, SCREEN_HEIGHT * 0.1);
        options_button.setOnAction(event -> {
            // TODO optionsButton
        });

        Button exit_button = new Button("Exit");
        exit_button.setMinSize(SCREEN_WIDTH * 0.2, SCREEN_HEIGHT * 0.1);
        exit_button.setOnAction(event -> {
            this.exitApplication();
        });

        VBox menu_buttons = new VBox();
        menu_buttons.setSpacing(10);
        menu_buttons.setPadding(new Insets(0, 20, 10, 20));
        menu_buttons.getChildren().addAll(start_button, options_button, exit_button);
        menu_buttons.setAlignment(Pos.CENTER);

        this.menu.getChildren().add(menu_buttons);
        this.menu.setBackground(
            new Background(
                new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)
            )
        );

        return this.menu;
    }

    //creates the game, includes creating the board with tiles, meteorites & the player.
    private Parent createGame() {
        //create game screen.
        StackPane root = new StackPane();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        game = new Pane();
        game.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        go_menu = new Pane();
        go_menu.setVisible(false);

        //create score.
        String player_score = "Score: " + score;
        score_text = new Label(player_score);
        score_text.setFont(new Font(38));
        StackPane.setAlignment(score_text, Pos.TOP_CENTER);

        //create tiles with background + setting the neighbours of tiles.
        Image tile_background = new Image (new File(resources_path + "space-background.png").toURI().toString());

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, tile_background);
                grid[x][y] = tile;
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                grid[x][y].setNeighbours(getNeighbours(grid[x][y]));
            }
        }

        //create player.
        user = new User(new Image (new File(resources_path + "spaceship.png").toURI().toString()), grid[0][0], "up");
        grid[0][0].setObject(user);

        //creating Comets.
        for(int i = 0; i < comets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, X_TILES);
            int posY = ThreadLocalRandom.current().nextInt(1, X_TILES);

            while (!grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, X_TILES);
                posY = ThreadLocalRandom.current().nextInt(1, Y_TILES);
            }

            comets[i] = new Comet(new Image (new File(resources_path + "Meteorites.png").toURI().toString()), grid[posX][posY]);
            grid[posX][posY].setObject(comets[i]);
        }

        //creating planets
        this.planets = new Planet[this.planet_count];
        for (int i = 0; i < planets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, X_TILES);
            int posY = ThreadLocalRandom.current().nextInt(1, Y_TILES);

            while (!grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, X_TILES);
                posY = ThreadLocalRandom.current().nextInt(1, Y_TILES);
            }
            int random_image = ThreadLocalRandom.current().nextInt(1, 4);

            planets[i] = new Planet(new Image(new File(this.resources_path + "planet0"+ random_image +".png").toURI().toString()), this.grid[posX][posY]);
            this.grid[posX][posY].setObject(planets[i]);
        }

        //updating game.
        updateGame();

        //set all game objects in scene.
        root.getChildren().addAll(game, score_text, go_menu);
        return root;
    }

    //add an timer & set an input handler.
    private void addHandlers() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (game_over || game_won) {
                    timer.cancel();
                    return;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        score++;

                        for (Comet c :  comets) {
                            c.update();
                        }

                        updateGame();
                    }
                });
            }
        }, 0, 1000);

        game_scene.setOnKeyPressed(event -> {
            if (game_over || game_won) {
                setupGameOverMenu();
                return;
            }
            user.handleKeyPressed(event.getCode());
            int planets_visited = 0;
            for (Planet planet : planets) {
                if (planet.isVisited()) {
                    planets_visited++;
                }
            }

            if (!all_planets_visited && planets_visited == planets.length) {
                all_planets_visited = true;
                setWormhole();
            }
            updateGame();
        });
    }

    private void setupGameOverMenu() {
        go_menu.setVisible(true);

        //announce label.
        Label background = new Label();
        background.setStyle(" -fx-background-color: green;");
        background.setMinWidth(game.getWidth() / 3);
        background.setMinHeight(game.getHeight() / 3);
        background.setLayoutX(game.getWidth() / 3);
        background.setLayoutY(game.getHeight() / 3);

        //result label.
        String result_text = "";

        if(game_over) {
            result_text = "GAME OVER";
        } else if(game_won) {
            result_text = "GAME WON";
        }

        Label result = new Label(result_text);
        result.setFont(new Font(25));
        result.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        result.setLayoutY(background.getLayoutY());

        //Score label.
        String score_text = "Your score: " + score;
        Label score = new Label(score_text);
        score.setFont(new Font(25));
        score.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        score.setLayoutY(background.getLayoutY() + (background.getMinHeight() / 2));

        Button home_button = new Button("Main Menu");
        home_button.setMinSize(background.getMinWidth() * 0.2, background.getMinHeight() * 0.1);
        home_button.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        home_button.setLayoutY(background.getLayoutX());
        home_button.setOnAction(event -> {
            this.game_scene = new Scene(createMenu());
            this.addHandlers();
            this.primaryStage.setScene(this.menu_scene);
            resetGame();
        });

        go_menu.getChildren().addAll(background, result, score, home_button);
    }

    //creating wormhole after all planets have been visited.
    private void setWormhole () {
        int random_x = ThreadLocalRandom.current().nextInt(0, X_TILES);
        int random_y = ThreadLocalRandom.current().nextInt(0, Y_TILES);

        while (!grid[random_x][random_y].isAvailable()) {
            random_x = ThreadLocalRandom.current().nextInt(0, X_TILES);
            random_y = ThreadLocalRandom.current().nextInt(0, Y_TILES);
        }
        wormhole = new Wormhole(new Image (new File(resources_path + "wormhole.png").toURI().toString()), grid[random_x][random_y]);
        grid[random_x][random_y].setObject(wormhole);
    }

    //get the neighbours of the parameter tile.
    private List<Tile> getNeighbours (Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        Tile emptyTile = new Tile(-1, -1, null);

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

    //resetting game values
    private void resetGame() {
        game_over = false;
        game_won = false;
        score = -1;
    }

    //update & Render
    public void updateGame() {
        if (!game.getChildren().isEmpty()) {
            game.getChildren().clear();
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                game.getChildren().add(grid[x][y].getPane());
            }
        }
        int game_size_width = TILE_SIZE * X_TILES;
        int game_size_height = TILE_SIZE * Y_TILES;
        game.setTranslateX((SCREEN_WIDTH * 0.5) - (game_size_width * 0.5));
        game.setTranslateY((SCREEN_HEIGHT * 0.5) - (game_size_height * 0.5));
        String text = "Score: " + score;
        score_text.setText(text);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void exitApplication() {
        // TODO ? save to filesystem

        this.primaryStage.close();
    }
}
