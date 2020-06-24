package quest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

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

    private Stage stage;
    private Scene game_scene;
    private Scene options_scene;
    private Scene menu_scene;

    private Pane game;
    private Label score_text;
    private User user;
    private Integer score = -1;

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

        VBox menu_buttons = new VBox(start_button, options_button, exit_button);
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

        Label planet_count_label = new Label(
                String.format("Planets: %d", this.game_options.getPlanetCount())
        );
        Slider planet_count = new Slider(3, 100, this.game_options.getPlanetCount());
        planet_count.setMaxWidth(STAGE_WIDTH * .3);
        planet_count.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.game_options.setPlanetCount(newValue.intValue());
            planet_count_label.setText(
                    String.format("Planets: %d", this.game_options.getPlanetCount())
            );
        });

        Label comet_count_label = new Label(
                String.format("Comets: %d", this.game_options.getCometCount())
        );
        Slider comet_count = new Slider(5, 100, this.game_options.getCometCount());
        comet_count.setMaxWidth(STAGE_WIDTH * .3);
        comet_count.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.game_options.setCometCount(newValue.intValue());
            comet_count_label.setText(
                    String.format("Comets: %d", this.game_options.getCometCount())
            );
        });

        Button save_and_go_back = new Button("Save and go back");
        save_and_go_back.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        save_and_go_back.setOnAction(event -> {
            this.stage.setScene(this.menu_scene);
        });


        VBox options_box = new VBox(
                title, x_tile_count_label, x_tile_count, y_tile_count_label, y_tile_count,
                tile_size_label, tile_size, planet_count_label, planet_count,
                comet_count_label, comet_count, save_and_go_back
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
        game = new Pane();
        game.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);

        String player_score = "Score: " + score;
        score_text = new Label(player_score);
        score_text.setFont(new Font(38));
        StackPane.setAlignment(score_text, Pos.TOP_CENTER);

        Image tile_background = new Image (new File(RESOURCE_PATH + "space-background.png").toURI().toString());

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                Tile tile = new Tile(x, y, this.game_options.getTileSize(), tile_background);
                grid[x][y] = tile;
            }
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                grid[x][y].setNeighbours(getNeighbours(grid[x][y]));
            }
        }

        user = new User(new Image (new File(RESOURCE_PATH + "spaceship.png").toURI().toString()), grid[0][0], "up");
        grid[0][0].setObject(user);

        //creating Comets.
        for(int i = 0; i < comets.length; i++)
        {
            int posX = getRandom(1, this.game_options.getXTileCount() -1);
            int posY = getRandom(1, this.game_options.getYTileCount() -1);
            Comet comet = new Comet(new Image (new File(RESOURCE_PATH + "Meteorites.png").toURI().toString()), grid[posX][posY]);
            grid[posX][posY].setObject(comet);
            comets[i] = comet;
        }

        for (Planet planet : this.planets) {
            int position_x = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
            int position_y = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());
            Tile planet_tile = this.grid[position_x][position_y];
            planet = new Planet(
                    new Image(new File(RESOURCE_PATH + "planet_unknown.png").toURI().toString()),
                    new Image(new File(RESOURCE_PATH + "planet_earth.png").toURI().toString()),
                    planet_tile
            );
            planet_tile.setObject(planet);
        }

        updateGame();

        root.getChildren().addAll(game, score_text);

        return root;
    }

    //add an timer & set an input handler.
    private void addHandlers() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        score++;
                        lateUpdate();
                        updateGame();
                    }
                });
            }
        }, 0, 1000);

        this.game_scene.setOnKeyPressed(event -> {
            this.user.handleKeyPressed(event.getCode());
            this.updateGame();
        });
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
                neighbors.add(grid[newX][newY]);
            } else {
                neighbors.add(emptyTile);
            }
        }

        return neighbors;
    }

    //get a random number between a min & a max.
    private int getRandom(int min, int max)   {
        int temp = (int)(Math.random() * (max - min + 1) + min);
        return temp;
    }

    //update & Render
    public void updateGame() {
        if (!game.getChildren().isEmpty()) {
            game.getChildren().clear();
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                game.getChildren().add(grid[x][y].getPane());
            }
        }

        int game_size = this.game_options.getTileSize() * this.game_options.getXTileCount();
        game.setTranslateX((STAGE_WIDTH * 0.5) - (game_size * 0.5));
        game.setTranslateY((STAGE_HEIGHT * 0.5) - (game_size * 0.5));
        String text = "Score: " + score;
        score_text.setText(text);
    }

    //Update Object that update time based.
    public void lateUpdate() {
        //update comets.
        for (Comet c :  comets) {
            c.update();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
