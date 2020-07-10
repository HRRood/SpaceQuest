package quest;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game class.
 * the class in which the game is made, played en terminated.
 */
public class Game {

    private Main main;
    private GameOptions game_options;


    protected Tile[][] grid;
    protected Comet[] comets;
    protected Planet[] planets;
    protected Wormhole wormhole;
    protected User user;
    protected Timer timer;

    public Scene scene;
    private Pane game;
    private Pane go_menu;
    private Label score_text;
    private int score = -1;

    public static boolean game_over = false;
    public static boolean game_won = false;
    public static boolean all_planets_visited = false;

    /**
     * Game.
     * the Game class constructor.
     * Sets up the variables for the game.
     *
     * @param main
     * @param game_options
     */
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

        this.addHandlers();
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
     * getNeighbours.
     * The function that iterates through the arraylist of tiles,
     * and puts all tiles around the current tile in a arrasylist
     * and returns the arraylist with the neighbouring tiles.
     *
     * @param tile
     * @return
     */
    private List<Tile> getNeighbours (Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        Tile emptyTile = new Tile(-1, -1, this.game_options.getTileSize(), null);

        int[] points = new int[] {0, -1, -1, 0, 1, 0, 0, 1};

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

    /**
     * GenerateScore.
     * sets up the score.
     *
     */
    private void generateScore() {
        String player_score = "Score: " + this.score;
        this.score_text = new Label(player_score);
        this.score_text.setFont(Main.FONT_40);
        StackPane.setAlignment(this.score_text, Pos.TOP_CENTER);
    }

    /**
     * GenerateGrid.
     * sets up the grid with background on each tile.
     */
    private void generateGrid() {
        Image tile_background = new Image(Main.FullResourcePath("space-background.png"));

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                this.grid[x][y] = new Tile(x, y, this.game_options.getTileSize(), tile_background);
            }
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                this.grid[x][y].setNeighbours(this.getNeighbours(this.grid[x][y]));
            }
        }
    }

    /**
     * GenerateUser.
     * sets up player and puts it in the grid.
     */
    private void generateUser() {
        Image user_image = new Image (Main.FullResourcePath("spaceship.png"));
        this.user = new User(user_image, this.grid[0][0], "up");
        this.grid[0][0].setObject(this.user);
    }

    /**
     * GenerateComets.
     * generates a numbers of comets equal to the inputted game options.
     * gives them an random position and puts them in the grid.
     */
    private void generateComets() {
        Image comet_image = new Image(Main.FullResourcePath("Meteorites.png"));

        for (int i = 0; i < this.comets.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
            int posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());

            while (!this.grid[posX][posY].isAvailable()) {
                posX = ThreadLocalRandom.current().nextInt(1, this.game_options.getXTileCount());
                posY = ThreadLocalRandom.current().nextInt(1, this.game_options.getYTileCount());
            }

            this.comets[i] = new Comet(comet_image, this.grid[posX][posY]);
            this.grid[posX][posY].setObject(this.comets[i]);
        }
    }

    /**
     * GeneratePlanets.
     * generates a numbers of comets equal to the inputted game options.
     * gives them an random position and puts them in the grid.
     *
     */
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

    /**
     * Update.
     * update the grid, player, comets, planets & score.
     */
    protected void update() {
        if (!this.game.getChildren().isEmpty()) {
            this.game.getChildren().clear();
        }

        for (int y = 0; y < this.game_options.getYTileCount(); y++) {
            for (int x = 0; x < this.game_options.getXTileCount(); x++) {
                this.game.getChildren().add(this.grid[x][y].getPane());
            }
        }

        int game_size_x = this.game_options.getTileSize() * this.game_options.getXTileCount();
        int game_size_y = this.game_options.getTileSize() * this.game_options.getYTileCount();

        game.setTranslateX((Main.STAGE_WIDTH * 0.5) - (game_size_x * 0.5));
        game.setTranslateY((Main.STAGE_HEIGHT * 0.5) - (game_size_y * 0.5));

        this.score_text.setText("Score: " + this.score);
    }

    /**
     * AddHandlers.
     * adds handlers to the game.
     * adds and runs the timer for the game.
     * adds input functions for the player.
     * Checks for a game over or game win.
     *
     */
    private void addHandlers() {
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            Platform.runLater(() -> {
                if (game_over || game_won) {
                    timer.cancel();
                    timer.purge();
                    return;
                }
                score++;

                for (Comet c : comets) {
                    c.update();
                }

                update();
                if (game_over || game_won) {
                    timer.cancel();
                    timer.purge();
                    setupGameOverMenu();
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

            if (!Game.all_planets_visited && planets_visited == this.planets.length) {
                Game.all_planets_visited = true;
                this.setWormhole();
            }

            this.update();

            if (game_over || game_won) {
                this.setupGameOverMenu();
            }
        });
    }

    /**
     * setupGameOverMenu.
     * creates the game over/win popup menu.
     *
     */
    public void setupGameOverMenu() {
        this.go_menu.setVisible(true);

        Rectangle container = new Rectangle();
        container.setWidth(this.game.getWidth() / 2);
        container.setHeight(this.game.getHeight() / 2);
        container.setArcWidth(20);
        container.setArcHeight(20);
        container.setFill(Color.GRAY);
        container.relocate((this.game.getWidth() / 2) - (container.getWidth() / 2), (this.game.getHeight() / 2) - container.getHeight() / 2);

        String result_text = "";

        if(game_over) {
            result_text = "GAME OVER";
        } else if(game_won) {
            result_text = "GAME WON";
        }

        Text result = new Text(result_text);
        result.setFont(Main.FONT_130);
        result.relocate((container.getWidth()) - result.getLayoutBounds().getWidth() * 0.5, container.getLayoutY() + 100);

        String score_text = "Score does not count";
        if (game_won) {
            score_text = "Your score: " + this.score;
        }
        Text score = new Text(score_text);
        score.setFont(Main.FONT_40);
        score.relocate((container.getWidth()) - score.getLayoutBounds().getWidth() * 0.5, container.getLayoutY() + 200);

        Button home_button = new Button("Main Menu");
        home_button.setFont(Main.FONT_20);
        home_button.setMinSize(container.getWidth() * 0.2, container.getHeight() * 0.1);
        home_button.relocate((container.getWidth()) - (home_button.getMinWidth() * 0.5), container.getLayoutY() + container.getHeight() - 100);

        home_button.setOnAction(e -> {
            this.reset();
            main.gotoMenu();
        });

        this.go_menu.getChildren().addAll(container, result, score, home_button);
    }

    /**
     * Reset.
     * resets variables for restarting the game.
     */
    private void reset() {
        Game.game_over = false;
        Game.game_won = false;
        all_planets_visited = false;
    }

    /**
     * SetWormhole.
     * sets up a wormhole when all planets has been visited.
     * gives it a sprite and a random position and puts it in the grid.
     */
    public void setWormhole () {
        int random_x = ThreadLocalRandom.current().nextInt(0, this.game_options.getXTileCount());
        int random_y = ThreadLocalRandom.current().nextInt(0, this.game_options.getYTileCount());

        while (!this.grid[random_x][random_y].isAvailable()) {
            random_x = ThreadLocalRandom.current().nextInt(0, this.game_options.getXTileCount());
            random_y = ThreadLocalRandom.current().nextInt(0, this.game_options.getYTileCount());
        }

        Image wormhole_image = new Image(Main.FullResourcePath("wormhole.png"));

        wormhole = new Wormhole(wormhole_image, this.grid[random_x][random_y]);
        this.grid[random_x][random_y].setObject(this.wormhole);
    }

    /**
     * GetGrid.
     * return the grid.
     *
     * @return
     */
    public Tile[][] getGrid() {
        return this.grid;
    }

    /**
     * GetComets.
     * returns the comet arrayList.
     *
     * @return
     */
    public Comet[] getComets() {
        return this.comets;
    }

    /**
     * GetPlanets.
     * returns the planet arrayList.
     *
     * @return
     */
    public Planet[] getPlanets() {
        return this.planets;
    }

}
