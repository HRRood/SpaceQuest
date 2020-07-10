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
        Direction direction = Direction.values()[Game.RandomInt(0, 4)];

        while (
                this.tile.getNeighbourFromDirection(direction) != null 
                && !this.tile.getNeighbourFromDirection(direction).isAvailable()
        ) {
            direction = Direction.values()[Game.RandomInt(0, 4)];
        }

        return this.moveObjectOK(direction);
    }

}
