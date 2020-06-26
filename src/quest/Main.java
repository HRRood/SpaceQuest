package quest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sun.tools.jconsole.JConsole;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {

    private static final String RESOURCE_PATH = "src/quest/Resources/";
    private static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final int STAGE_WIDTH = (int) SCREEN_WIDTH;
    private static final int STAGE_HEIGHT = (int) SCREEN_HEIGHT;
    private static final int BUTTON_WIDTH = (int) (SCREEN_WIDTH * .2);
    private static final int BUTTON_HEIGHT = (int) (SCREEN_HEIGHT * .1);

    private GameOptions game_options;

    private Tile[][] grid;
    private Comet[] comets;
    private Planet[] planets;
    private Wormhole wormhole;
    private User user;

    private Stage stage;
    private Scene game_scene;
    private Scene options_scene;
    private Scene menu_scene;

    private Pane game;
    private Pane go_menu;
    private Label score_text;
    private Integer score = -1;

    public static boolean game_over = false;
    public static boolean game_won = false;
    public boolean all_planets_visited = false;

    @Override
    public void start(Stage stage) throws Exception {
        this.game_options = new GameOptions();

        this.menu_scene = this.createMenuScene();
        this.options_scene = this.createOptionsScene();

        this.stage = stage;
        this.stage.setTitle("Space Quest");
        this.stage.setScene(this.menu_scene);
        this.stage.setMaximized(true);
        this.stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.stage.close();
    }

    //creates the menu.
    private Scene createMenuScene() {
        Text text = new Text("Space Quest");
        text.setFont(Font.loadFont(Main.class.getResource("pixel.ttf").toExternalForm(), 130));

        Button start_button = new Button("Start Game");
        start_button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        start_button.setOnAction(event -> {
            this.game_scene = new Scene(createGame());
            this.addHandlers();
            this.stage.setScene(this.game_scene);
        });

        Button options_button = new Button("Game Options");
        options_button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        options_button.setOnAction(event -> {
            this.stage.setScene(this.options_scene);
        });

        Button exit_button = new Button("Exit");
        exit_button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit_button.setOnAction(event -> {
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox menu_buttons = new VBox(text, start_button, options_button, exit_button);
        menu_buttons.setSpacing(10);
        menu_buttons.setAlignment(Pos.CENTER);

        return new Scene(menu_buttons, STAGE_WIDTH, STAGE_HEIGHT, Color.DARKGRAY);
    }

    private Scene createOptionsScene() {
        Text title = new Text("Game Options");
        title.setFont(Font.font(20));

        Label x_tile_count_label = new Label(
                String.format("Tiles in width: %d", this.game_options.getXTileCount())
        );
        Slider x_tile_count = new Slider(12, 100, this.game_options.getXTileCount());
        x_tile_count.setMaxWidth(STAGE_WIDTH * .3);
        x_tile_count.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.game_options.setXTileCount(newValue.intValue());
            x_tile_count_label.setText(
                String.format("Tiles in width: %d", this.game_options.getXTileCount())
            );
        });

        Label y_tile_count_label = new Label(
                String.format("Tiles in height: %d", this.game_options.getYTileCount())
        );
        Slider y_tile_count = new Slider(12, 100, this.game_options.getXTileCount());
        y_tile_count.setMaxWidth(STAGE_WIDTH * .3);
        y_tile_count.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.game_options.setYTileCount(newValue.intValue());
            y_tile_count_label.setText(
                String.format("Tiles in height: %d", this.game_options.getYTileCount())
            );
        });

        Label tile_size_label = new Label(
            String.format("Tile size: %d", this.game_options.getTileSize())
        );
        Slider tile_size = new Slider(10, 100, this.game_options.getTileSize());
        tile_size.setMaxWidth(STAGE_WIDTH * .3);
        tile_size.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.game_options.setTileSize(newValue.intValue());
            tile_size_label.setText(
                String.format("Tile size: %d", this.game_options.getTileSize())
            );
        });

        Button save_and_go_back = new Button("Save and go back");
        save_and_go_back.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        save_and_go_back.setOnAction(event -> {
            this.stage.setScene(this.menu_scene);
        });

        VBox options_box = new VBox(
            title, x_tile_count_label, x_tile_count, y_tile_count_label, y_tile_count,
            tile_size_label, tile_size, save_and_go_back
        );
        options_box.setSpacing(10);
        options_box.setAlignment(Pos.CENTER);

        return new Scene(options_box, STAGE_WIDTH, STAGE_HEIGHT, Color.DARKGRAY);
    }

    //creates the game, includes creating the board with tiles, meteorites & the player.
    private Parent createGame() {
        this.grid = new Tile[this.game_options.getXTileCount()][this.game_options.getYTileCount()];
        this.comets = new Comet[this.game_options.getCometCount()];
        this.planets = new Planet[this.game_options.getPlanetCount()];

        StackPane root = new StackPane();
        root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.game = new Pane();
        this.game.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.go_menu = new Pane();
        this.go_menu.setVisible(false);

        //create score.
        String player_score = "Score: " + this.score;
        this.score_text = new Label(player_score);
        this.score_text.setFont(new Font(38));
        StackPane.setAlignment(this.score_text, Pos.TOP_CENTER);

        //create tiles with background + setting the neighbours of tiles.
        Image tile_background = new Image (new File(RESOURCE_PATH + "space-background.png").toURI().toString());

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                Tile tile = new Tile(x, y, this.game_options.getTileSize(), tile_background);
                grid[x][y] = tile;
            }
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                grid[x][y].setNeighbours(this.getNeighbours(grid[x][y]));
            }
        }

        //create player.
        Image user_image = new Image (
            new File(RESOURCE_PATH + "spaceship.png").toURI().toString()
        );
        this.user = new User(user_image, grid[0][0], "up");
        this.grid[0][0].setObject(this.user);

        //creating Comets.
        Image comet_image = new Image (
                new File(RESOURCE_PATH + "Meteorites.png").toURI().toString()
        );
        for(int i = 0; i < this.comets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
            int posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());

            while (!this.grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
                posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());
            }

            this.comets[i] = new Comet(comet_image, grid[posX][posY]);
            this.grid[posX][posY].setObject(this.comets[i]);
        }

        //creating planets
        for (int i = 0; i < this.planets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
            int posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());

            while (!grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
                posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());
            }

            int random_image_index = ThreadLocalRandom.current().nextInt(1, 4);
            Image planet_image = new Image(
                    new File(RESOURCE_PATH + "planet0" + random_image_index +".png").toURI().toString()
            );

            this.planets[i] = new Planet(planet_image, this.grid[posX][posY]);
            this.grid[posX][posY].setObject(this.planets[i]);
        }

        //updating game.
        this.updateGame();

        root.getChildren().addAll(this.game, this.score_text, this.go_menu);
        return root;
    }

    //add an timer & set an input handler.
    private void addHandlers() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Main.game_over || Main.game_won) {
                    timer.cancel();
                    return;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        score++;

                        for (Comet c : comets) {
                            c.update();
                        }

                        updateGame();
                    }
                });
            }
        }, 0, 1000);

        this.game_scene.setOnKeyPressed(event -> {
            if (Main.game_over || Main.game_won) {
                return;
            }
            this.user.handleKeyPressed(event.getCode());
            int planets_visited = 0;
            for (Planet planet : this.planets) {
                if (planet.isVisited()) {
                    planets_visited++;
                }
            }

            if (!this.all_planets_visited && planets_visited == this.planets.length) {
                this.all_planets_visited = true;
                this.setWormhole();
            }
            this.updateGame();

            if (Main.game_over || Main.game_won) {
                this.setupGameOverMenu();
            }
        });
    }

    private void setupGameOverMenu() {
        this.go_menu.setVisible(true);

        //announce label.
        Label background = new Label();
        background.setStyle(" -fx-background-color: green;");
        background.setMinWidth(this.game.getWidth() / 3);
        background.setMinHeight(this.game.getHeight() / 3);
        background.setLayoutX(this.game.getWidth() / 3);
        background.setLayoutY(this.game.getHeight() / 3);

        //result label.
        String result_text = "";

        if(Main.game_over) {
            result_text = "GAME OVER";
        } else if(Main.game_won) {
            result_text = "GAME WON";
        }

        Label result = new Label(result_text);
        result.setFont(new Font(25));
        result.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        result.setLayoutY(background.getLayoutY());

        //Score label.
        String score_text = "Your score: " + this.score;
        Label score = new Label(score_text);
        score.setFont(new Font(25));
        score.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        score.setLayoutY(background.getLayoutY() + (background.getMinHeight() / 2));

        Button home_button = new Button("Main Menu");
        home_button.setMinSize(background.getMinWidth() * 0.2, background.getMinHeight() * 0.1);
        home_button.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        home_button.setLayoutY(background.getLayoutX());
        home_button.setOnAction(event -> {
            this.game_scene = this.createMenuScene();
            this.addHandlers();
            this.stage.setScene(this.menu_scene);
            resetGame();
        });

        this.go_menu.getChildren().addAll(background, result, score, home_button);
    }

    //creating wormhole after all planets have been visited.
    private void setWormhole () {
        int random_x = ThreadLocalRandom.current().nextInt(0, this.game_options.getXTileCount());
        int random_y = ThreadLocalRandom.current().nextInt(0, this.game_options.getYTileCount());

        while (!this.grid[random_x][random_y].isAvailable()) {
            random_x = ThreadLocalRandom.current().nextInt(0, this.game_options.getXTileCount());
            random_y = ThreadLocalRandom.current().nextInt(0, this.game_options.getYTileCount());
        }

        Image wormhole_image = new Image (
            new File(RESOURCE_PATH + "wormhole.png").toURI().toString()
        );

        this.wormhole = new Wormhole(wormhole_image, this.grid[random_x][random_y]);
        this.grid[random_x][random_y].setObject(this.wormhole);
    }

    //get the neighbours of the parameter tile.
    private List<Tile> getNeighbours (Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        Tile emptyTile = new Tile(-1, -1, this.game_options.getTileSize(), null);

        int[] points = new int[] {
                0, -1, -1, 0, 1, 0, 0, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getPosition_x() + dx;
            int newY = tile.getPosition_y() + dy;

            if (newX >= 0 && newX < this.game_options.getXTileCount() && newY >= 0 && newY < this.game_options.getYTileCount()) {
                neighbors.add(this.grid[newX][newY]);
            } else {
                neighbors.add(emptyTile);
            }
        }

        return neighbors;
    }

    //resetting game values
    private void resetGame() {
        Main.game_over = false;
        Main.game_won = false;
        this.score = -1;
        this.all_planets_visited = false;
    }

    //update & Render
    public void updateGame() {
        if (!this.game.getChildren().isEmpty()) {
            this.game.getChildren().clear();
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                this.game.getChildren().add(this.grid[x][y].getPane());
            }
        }

        int game_size = this.game_options.getTileSize() * this.game_options.getXTileCount();

        game.setTranslateX((STAGE_WIDTH * 0.5) - (game_size * 0.5));
        game.setTranslateY((STAGE_HEIGHT * 0.5) - (game_size * 0.5));

        this.score_text.setText("Score: " + this.score);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
