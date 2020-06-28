package quest;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private Main main;
    private GameOptions game_options;

    private Tile[][] grid;
    private Comet[] comets;
    private Planet[] planets;
    private Wormhole wormhole;
    private User user;

    private Pane game;
    private Pane go_menu;
    private Label score_text;
    private Integer score = -1;

    public static boolean game_over = false;
    public static boolean game_won = false;
    public boolean all_planets_visited = false;

    private Scene scene;

    public Game(Main main, GameOptions game_options) {
        this.main = main;
        this.game_options = game_options;

        this.grid = new Tile[this.game_options.getXTileCount()][this.game_options.getYTileCount()];
        this.comets = new Comet[this.game_options.getCometCount()];
        this.planets = new Planet[this.game_options.getPlanetCount()];

        StackPane root = new StackPane();
        root.setPrefSize(Main.STAGE_WIDTH, Main.STAGE_HEIGHT);

        this.game = new Pane();
        this.game.setPrefSize(Main.STAGE_WIDTH, Main.STAGE_HEIGHT);

        this.go_menu = new Pane();
        this.go_menu.setVisible(false);

        this.generateScore();
        this.generateGrid();
        this.generateUser();
        this.generateComets();
        this.generatePlanets();

        this.update();

        root.getChildren().addAll(this.game, this.score_text, this.go_menu);
        this.scene = new Scene(root);

        this.addHandlers(main);
    }

    public Scene getScene() {
        return this.scene;
    }

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

    private void generateScore() {
        String player_score = "Score: " + this.score;
        this.score_text = new Label(player_score);
        this.score_text.setFont(Main.FONT_40);
        StackPane.setAlignment(this.score_text, Pos.TOP_CENTER);
    }

    private void generateGrid() {
        Image tile_background = new Image(Main.FullResourcePath("space-background.png"));

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                grid[x][y] = new Tile(x, y, this.game_options.getTileSize(), tile_background);
            }
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                grid[x][y].setNeighbours(this.getNeighbours(grid[x][y]));
            }
        }
    }

    private void generateUser() {
        Image user_image = new Image (Main.FullResourcePath("spaceship.png"));
        this.user = new User(user_image, grid[0][0], "up");
        this.grid[0][0].setObject(this.user);
    }

    private void generateComets() {
        Image comet_image = new Image(Main.FullResourcePath("Meteorites.png"));

        for (int i = 0; i < this.comets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
            int posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());

            while (!this.grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
                posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());
            }

            this.comets[i] = new Comet(comet_image, grid[posX][posY]);
            this.grid[posX][posY].setObject(this.comets[i]);
        }
    }

    private void generatePlanets() {
        for (int i = 0; i < this.planets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
            int posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());

            while (!grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
                posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());
            }

            Image planet_image = new Image(Main.FullResourcePath(
                    String.format("planet0%d.png", ThreadLocalRandom.current().nextInt(1, 4))
            ));

            this.planets[i] = new Planet(planet_image, this.grid[posX][posY]);
            this.grid[posX][posY].setObject(this.planets[i]);
        }
    }

    private void update() {
        if (!this.game.getChildren().isEmpty()) {
            this.game.getChildren().clear();
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                this.game.getChildren().add(this.grid[x][y].getPane());
            }
        }

        int game_size = this.game_options.getTileSize() * this.game_options.getXTileCount();

        game.setTranslateX((Main.STAGE_WIDTH * 0.5) - (game_size * 0.5));
        game.setTranslateY((Main.STAGE_HEIGHT * 0.5) - (game_size * 0.5));

        this.score_text.setText("Score: " + this.score);
    }

    private void addHandlers(Main main) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        score++;

                        for (Comet c : comets) {
                            c.update();
                        }

                        update();
                        if (game_over || game_won) {
                            timer.cancel();
                            setupGameOverMenu();
                        }
                    }
                });
            }
        }, 0, 1000);

        this.scene.setOnKeyPressed(event -> {
            if (game_over || game_won) {
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

            this.update();

            if (game_over || game_won) {
                this.setupGameOverMenu();
            }
        });
    }

    public void setupGameOverMenu() {
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
        String score_text = "Your score: " + this.score;
        Label score = new Label(score_text);
        score.setFont(new Font(25));
        score.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        score.setLayoutY(background.getLayoutY() + (background.getMinHeight() / 2));

        Button home_button = new Button("Main Menu");
        home_button.setMinSize(background.getMinWidth() * 0.2, background.getMinHeight() * 0.1);
        home_button.setLayoutX(background.getLayoutX() + (background.getMinWidth() / 3));
        home_button.setLayoutY(background.getLayoutX());
        home_button.setOnAction(e -> {
            this.reset();
            main.gotoMenu();
        });

        this.go_menu.getChildren().addAll(background, result, score, home_button);
    }

    private void reset() {
        Game.game_over = false;
        Game.game_won = false;
    }

    private void setWormhole () {
        int random_x = ThreadLocalRandom.current().nextInt(0, this.game_options.getXTileCount());
        int random_y = ThreadLocalRandom.current().nextInt(0, this.game_options.getYTileCount());

        while (!this.grid[random_x][random_y].isAvailable()) {
            random_x = ThreadLocalRandom.current().nextInt(0, this.game_options.getXTileCount());
            random_y = ThreadLocalRandom.current().nextInt(0, this.game_options.getYTileCount());
        }

        Image wormhole_image = new Image(Main.FullResourcePath("wormhole.png"));

        this.wormhole = new Wormhole(wormhole_image, this.grid[random_x][random_y]);
        this.grid[random_x][random_y].setObject(this.wormhole);
    }

    public Tile[][] getGrid() {
        return this.grid;
    }

}
