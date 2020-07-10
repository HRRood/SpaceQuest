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

    private Tile[] tiles;
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

        // this.run();
    }

    private void generateTiles() {
        int denominator = this.quest.getDifficulty().tileCount;
        int upperBound = denominator * denominator;

        this.tiles = new Tile[upperBound];

        Image backgroundImage = new Image(Game.GetFile("img/tile.png").toURI().toString());
        for (int i = 0; i < upperBound; i++) {
            this.tiles[i] = new Tile(this.quest, backgroundImage);
        }

        for (int i = 0; i < upperBound; i++) {
            this.tiles[i].neighbours.top = ((i + 1) - denominator) > 0 ? this.tiles[i - denominator] : null;
            this.tiles[i].neighbours.right = (i + 1) < upperBound && (i + 1) % denominator != 0 ? this.tiles[(i + 1)] : null;
            this.tiles[i].neighbours.bottom = (i + denominator) < upperBound ? this.tiles[i + denominator] : null;
            this.tiles[i].neighbours.left = i != 0 && i % denominator != 0 ? this.tiles[i - 1] : null;
        }
    }

    private void generateSpaceShip() {
        Image sprite = new Image(Game.GetFile("img/space_ship.png").toURI().toString());

        this.spaceShip = new SpaceShip(sprite, this.tiles[0], Direction.RIGHT);
        this.tiles[0].objects.spaceShip = this.spaceShip;
    }

    private void generatePlanets() {
        this.planets = new Planet[this.quest.getDifficulty().planetCount];

        Image[] sprites = new Image[3];
        sprites[0] = new Image(Game.GetFile("img/planet_0.png").toURI().toString());
        sprites[1] = new Image(Game.GetFile("img/planet_1.png").toURI().toString());
        sprites[2] = new Image(Game.GetFile("img/planet_2.png").toURI().toString());

        for (int i = 0; i < this.quest.getDifficulty().planetCount; i++) {
            int randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);

            while (!this.tiles[randomInt].isAvailable()) {
                randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);
            }

            this.planets[i] = new Planet(sprites[Game.RandomInt(0, 3)], this.tiles[randomInt]);
            this.tiles[randomInt].objects.planet = this.planets[i];
        }
    }

    private void generateComets() {
        this.comets = new Comet[this.quest.getDifficulty().cometCount];

        Image sprite = new Image(Game.GetFile("img/comet.png").toURI().toString());

        for (int i = 0; i < this.quest.getDifficulty().cometCount; i++) {
            int randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);

            while (!this.tiles[randomInt].isAvailable()) {
                randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);
            }

            this.comets[i] = new Comet(sprite, this.tiles[randomInt]);
            this.tiles[randomInt].objects.comet = this.comets[i];
        }
    }

    private void generateSpacePirates() {
        this.spacePirates = new SpacePirate[this.quest.getDifficulty().spacePirateCount];

        Image sprite = new Image(Game.GetFile("img/space_pirate.png").toURI().toString());

        for (int i = 0; i < this.quest.getDifficulty().spacePirateCount; i++) {
            int randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);

            while (!this.tiles[randomInt].isAvailable()) {
                randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);
            }

            this.spacePirates[i] = new SpacePirate(sprite, this.tiles[randomInt], Direction.RIGHT);
            this.tiles[randomInt].objects.spacePirate = this.spacePirates[i];
        }
    }

    public void generateWormhole() {
        Image sprite = new Image(Game.GetFile("img/wormhole.png").toURI().toString());

        int randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);

        while (!this.tiles[randomInt].isAvailable()) {
            randomInt = Game.RandomInt(0, this.quest.getDifficulty().tileCount * this.quest.getDifficulty().tileCount);
        }

        this.wormhole = new Wormhole(sprite, this.tiles[randomInt]);
        this.tiles[randomInt].objects.wormhole = this.wormhole;
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

            for (Comet comet : comets) {
                if (comet.MoveRandomOK() == CollusionType.SPACESHIP) {
                    quest.defeat = true;
                }
            }

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

        for (int i = 0; i < this.quest.getDifficulty().tileCount; i++) {
            for (int j = 0; j < this.quest.getDifficulty().tileCount; j++) {
                gridPane.add(this.tiles[j + this.quest.getDifficulty().tileCount * i].render(), j, i);
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

    public Scene getScene() {
        return this.scene;
    }

}
