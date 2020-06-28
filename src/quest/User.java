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
                moveObject("up");
                break;
            }
            case S:
            case DOWN: {
                moveObject("down");
                break;
            }
            case A:
            case LEFT: {
                moveObject("left");
                break;
            }
            case D:
            case RIGHT: {
                moveObject("right");
                break;
            }
        }
    }

}
