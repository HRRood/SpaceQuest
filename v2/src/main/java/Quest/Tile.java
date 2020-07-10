package main.java.Quest;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import main.java.Quest.Object.*;

public class Tile {

    private final Quest quest;
    private final Background background;
    public Neighbours neighbours;
    public QuestObjects objects;

    public Tile(Quest quest, Image backgroundImage) {
        this.quest = quest;
        this.background = this.backgroundFromImage(backgroundImage);
        this.neighbours = new Neighbours();
        this.objects = new QuestObjects();
    }

    public StackPane render() {
        StackPane stackPane = new StackPane();
        stackPane.setMinSize(this.computeWidth(.8), this.computeHeight(.8));
        stackPane.setMaxSize(this.computeWidth(.8), this.computeHeight(.8));
        stackPane.setBackground(this.background);

        if (this.objects.planet != null) {
            ImageView imageView = this.imageView(this.objects.planet.getSprite());

            if (this.objects.planet.visited) {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-.5);
                imageView.setEffect(colorAdjust);
            }

            stackPane.getChildren().add(imageView);
        }

        if (this.objects.comet != null) {
            stackPane.getChildren().add(this.imageView(this.objects.comet.getSprite()));
        }

        if (this.objects.wormhole != null) {
            stackPane.getChildren().add(this.imageView(this.objects.wormhole.getSprite()));
        }

        if (this.objects.spacePirate != null) {
            ImageView imageView = this.imageView(
                    this.objects.spacePirate.getSprite(),
                    this.objects.spacePirate.direction.getRadiant()
            );
            stackPane.getChildren().add(imageView);
        }

        if (this.objects.spaceShip != null) {
            ImageView imageView = this.imageView(
                    this.objects.spaceShip.getSprite(),
                    this.objects.spaceShip.direction.getRadiant()
            );
            stackPane.getChildren().add(imageView);
        }

        return stackPane;
    }

    private double computeWidth(double multiplier) {
        return (this.quest.game.stage.getWidth() * multiplier) / this.quest.getDifficulty().tileCount;
    }

    private double computeHeight(double multiplier) {
        return (this.quest.game.stage.getHeight() * multiplier) / this.quest.getDifficulty().tileCount;
    }

    private Background backgroundFromImage(Image image) {
        return new Background(
                new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(
                                100,
                                100,
                                true,
                                true,
                                true,
                                true
                        )
                )
        );
    }

    private ImageView imageView(Image image, int... rotate) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(this.computeWidth(.7));
        imageView.setFitHeight(this.computeHeight(.7));
        imageView.setPreserveRatio(true);
        if (rotate.length > 0) {
            imageView.setRotate(rotate[0]);
        }
        return imageView;
    }

    public boolean isAvailable() {
        return this.objects.planet == null
                && this.objects.comet == null
                && this.objects.wormhole == null
                && this.objects.spacePirate == null
                && this.objects.spaceShip == null;
    }

    public Tile getNeighbourFromDirection(Direction direction) {
        switch (direction) {
            case UP:
                return this.neighbours.top;
            case RIGHT:
                return this.neighbours.right;
            case DOWN:
                return this.neighbours.bottom;
            case LEFT:
            default:
                return this.neighbours.left;
        }
    }

    public static class Neighbours {

        public Tile top = null;
        public Tile right = null;
        public Tile bottom = null;
        public Tile left = null;

    }

    public static class QuestObjects {

        public Planet planet = null;
        public Comet comet = null;
        public Wormhole wormhole = null;
        public SpacePirate spacePirate = null;
        public SpaceShip spaceShip = null;

    }

}
