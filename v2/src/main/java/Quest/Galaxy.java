package main.java.Quest;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.Game.Game;
import main.java.Game.FontSize;
import main.java.Quest.Object.*;

import java.util.Timer;
import java.util.TimerTask;

public class Galaxy {

    private final Quest quest;

    private final Scene scene;
    private VBox vBox;

    private Tile[][] tiles;
    private Planet[] planets;
    private Comet[] comets;
    private Wormhole wormhole;
    private SpaceShip spaceShip;
    private SpacePirate[] spacePirates;

    public Galaxy(Quest quest) {
        this.quest = quest;

        this.generateTiles();
        this.generateSpaceShip();
        this.generatePlanets();
        this.generateComets();
        this.generateSpacePirates();

        this.scene = this.render();

        this.run();
    }

    private void generateTiles() {
        this.tiles = new Tile[this.quest.getDifficulty().xTileCount][this.quest.getDifficulty().yTileCount];

        Image backgroundImage = new Image(Game.GetFile("img/tile.png").toURI().toString());
        for (int x = 0; x < this.quest.getDifficulty().xTileCount; x++) {
            for (int y = 0; y < this.quest.getDifficulty().yTileCount; y++) {
                this.tiles[x][y] = new Tile(this.quest, backgroundImage);
            }
        }

        for (int x = 0; x < this.quest.getDifficulty().xTileCount; x++) {
            for (int y = 0; y < this.quest.getDifficulty().yTileCount; y++) {
                this.tiles[x][y].neighbours.top = (y - 1) >= 0 ?  tiles[x][y-1] : null;
                this.tiles[x][y].neighbours.right = (x + 1) < this.quest.getDifficulty().xTileCount ? tiles[x+1][y] : null;
                this.tiles[x][y].neighbours.bottom = (y + 1) < this.quest.getDifficulty().yTileCount ?  tiles[x][y+1] : null;
                this.tiles[x][y].neighbours.left = (x - 1) >= 0 ?  tiles[x-1][y] : null;
            }
        }
    }

    private void generateSpaceShip() {
        Image sprite = new Image(Game.GetFile("img/space_ship.png").toURI().toString());

        this.spaceShip = new SpaceShip(sprite, this.tiles[0][0], Direction.RIGHT);
        this.tiles[0][0].objects.spaceShip = this.spaceShip;
    }

    private void generatePlanets() {
        this.planets = new Planet[this.quest.getDifficulty().planetCount];

        Image[] sprites = new Image[3];
        sprites[0] = new Image(Game.GetFile("img/planet_0.png").toURI().toString());
        sprites[1] = new Image(Game.GetFile("img/planet_1.png").toURI().toString());
        sprites[2] = new Image(Game.GetFile("img/planet_2.png").toURI().toString());

        for (int i = 0; i < this.quest.getDifficulty().planetCount; i++) {
            Tile tile = this.randomTile();
            this.planets[i] = new Planet(sprites[Game.RandomInt(0, 3)], tile);
            tile.objects.planet = this.planets[i];
        }
    }

    private void generateComets() {
        this.comets = new Comet[this.quest.getDifficulty().cometCount];

        Image sprite = new Image(Game.GetFile("img/comet.png").toURI().toString());

        for (int i = 0; i < this.quest.getDifficulty().cometCount; i++) {
            Tile tile = this.randomTile();
            this.comets[i] = new Comet(sprite, tile);
            tile.objects.comet = this.comets[i];
        }
    }

    private void generateSpacePirates() {
        this.spacePirates = new SpacePirate[this.quest.getDifficulty().spacePirateCount];

        Image sprite = new Image(Game.GetFile("img/space_pirate.png").toURI().toString());

        for (int i = 0; i < this.quest.getDifficulty().spacePirateCount; i++) {
            Tile tile = this.randomTile();
            this.spacePirates[i] = new SpacePirate(sprite, tile, Direction.RIGHT);
            tile.objects.spacePirate = this.spacePirates[i];
        }
    }

    public void generateWormhole() {
        Image sprite = new Image(Game.GetFile("img/wormhole.png").toURI().toString());

        Tile tile = this.randomTile();
        this.wormhole = new Wormhole(sprite, tile);
        tile.objects.wormhole = this.wormhole;
    }

    public Scene render() {
        this.vBox = new VBox();
        this.vBox.setSpacing(15);
        this.vBox.setAlignment(Pos.CENTER);

        this.update();

        Scene scene = new Scene(this.vBox, this.quest.game.stage.getWidth(), this.quest.game.stage.getHeight(), Color.DARKGRAY);
        scene.setOnKeyPressed(event -> {
            if (this.quest.triumph || this.quest.defeat) {
                return;
            }

            switch (this.spaceShip.handleKeyPressedOK(event.getCode())) {
                case COMET:
                case SPACEPIRATE:
                    this.quest.defeat = true;
                    break;
                case WORMHOLE:
                    this.quest.triumph = true;
                    break;
            }

//            for (Comet comet : comets) {
//                if (comet.MoveRandomOK() == CollusionType.SPACESHIP) {
//                    quest.defeat = true;
//                }
//            }
//
//            for (SpacePirate spacePirate : spacePirates) {
//                if (spacePirate.MoveRandomOK() == CollusionType.SPACESHIP) {
//                    quest.defeat = true;
//                }
//            }

            if (this.wormhole == null) {
                int planetsVisited = 0;
                for (Planet planet : this.planets) {
                    if (planet.visited) {
                        planetsVisited++;
                    }
                }

                if (planetsVisited == this.planets.length) {
                    this.generateWormhole();
                }
            }

            if (this.quest.triumph || this.quest.defeat) {
                this.renderAndActivateOverlay();
                return;
            }

            this.update();
        });

        return scene;
    }

    public void update () {
        this.vBox.getChildren().clear();

        Text text = this.quest.game.objectBuilder.text("Score: " + this.quest.score, FontSize.MEDIUM);

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(this.quest.game.stage.getWidth() * .8, this.quest.game.stage.getHeight() * .8);
        gridPane.setMaxSize(this.quest.game.stage.getWidth() * .8, this.quest.game.stage.getHeight() * .8);

        for (int x = 0; x < this.quest.getDifficulty().xTileCount; x++) {
            for (int y = 0; y < this.quest.getDifficulty().yTileCount; y++) {
                gridPane.add(this.tiles[x][y].render(), x, y);
            }
        }

        this.vBox.getChildren().addAll(text, gridPane);
    }

    private void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (quest.triumph || quest.defeat) {
                        timer.cancel();
                        timer.purge();
                        return;
                    }

                    if (quest.score == 0) {
                        quest.defeat = true;
                    }

                    for (Comet comet : comets) {
                        if (comet.MoveRandomOK() == CollusionType.SPACESHIP) {
                            quest.defeat = true;
                        }
                    }

                    for (SpacePirate spacePirate : spacePirates) {
                        if (spacePirate.MoveRandomOK() == CollusionType.SPACESHIP) {
                            quest.defeat = true;
                        }
                    }

                    if (quest.defeat) {
                        timer.cancel();
                        timer.purge();
                        renderAndActivateOverlay();
                        return;
                    }

                    quest.score--;

                    update();
                });
            }
        }, 0, 1000);
    }

    public void renderAndActivateOverlay() {
        this.vBox.getChildren().clear();

        if (this.quest.triumph) {
            Text message = this.quest.game.objectBuilder.text("You win!", FontSize.LARGE);

            Text score = this.quest.game.objectBuilder.text("Score: " + this.quest.score, FontSize.MEDIUM);

            this.vBox.getChildren().addAll(message, score);
        } else {
            Text message = this.quest.game.objectBuilder.text("You lose.", FontSize.LARGE);

            this.vBox.getChildren().add(message);
        }

        Button button = this.quest.game.objectBuilder.button("Menu", FontSize.MEDIUM, event -> {
            this.quest.game.addQuest(this.quest);
        });

        this.vBox.getChildren().add(button);
    }

    private Tile randomTile() {
        int randomX = Game.RandomInt(0, this.quest.getDifficulty().xTileCount);
        int randomY = Game.RandomInt(0, this.quest.getDifficulty().yTileCount);

        while (!this.tiles[randomX][randomY].isAvailable()) {
            randomX = Game.RandomInt(0, this.quest.getDifficulty().xTileCount);
            randomY = Game.RandomInt(0, this.quest.getDifficulty().yTileCount);
        }

        return this.tiles[randomX][randomY];
    }

    public Scene getScene() {
        return this.scene;
    }

}
