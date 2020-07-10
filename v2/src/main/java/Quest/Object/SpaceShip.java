package main.java.Quest.Object;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import main.java.Quest.Tile;

public class SpaceShip extends MovableObject {

    public SpaceShip(Image sprite, Tile tile, Direction direction) {
        super(sprite, tile, direction);
    }

    public CollusionType handleKeyPressedOK(KeyCode keyCode) {
        switch (keyCode) {
            case W:
            case UP: {
                return this.moveObjectOK(Direction.UP);
            }
            case S:
            case DOWN: {
                return this.moveObjectOK(Direction.DOWN);
            }
            case A:
            case LEFT: {
                return this.moveObjectOK(Direction.LEFT);
            }
            case D:
            case RIGHT: {
                return this.moveObjectOK(Direction.RIGHT);
            }
            default:
                return CollusionType.IGNORE;
        }
    }

}
