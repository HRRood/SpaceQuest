package quest;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class User extends MovableObject {

    public User(Image sprite, Tile tile, String direction) {
        super(sprite, tile, direction);
    }

    public void handleKeyPressed (KeyCode code) {
        switch (code) {
            case W:
            case UP: {
                this.moveObject("up");
                break;
            }
            case S:
            case DOWN: {
                this.moveObject("down");
                break;
            }
            case A:
            case LEFT: {
                this.moveObject("left");
                break;
            }
            case D:
            case RIGHT: {
                this.moveObject("right");
                break;
            }
        }
    }

}
