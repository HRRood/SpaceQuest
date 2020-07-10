package main.java.Quest.Object;

import javafx.scene.image.Image;
import main.java.Game.Game;
import main.java.Quest.Tile;

public class AutomatedObject extends MovableObject {

    public AutomatedObject(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    public AutomatedObject(Image sprite, Tile tile, Direction direction) {
        super(sprite, tile, direction);
    }

    public CollusionType MoveRandomOK() {
        Direction direction = Direction.values()[Game.RandomInt(0, 3)];

        while (true) {

            Tile tile = this.tile.getNeighbourFromDirection(direction);

            if (tile == null) {
                direction = Direction.values()[Game.RandomInt(0, 3)];
            } else if (!tile.isAvailable()) {
                direction = Direction.values()[Game.RandomInt(0, 3)];
            } else {
                break;
            }
        }

        return this.moveObjectOK(direction);
    }

}
