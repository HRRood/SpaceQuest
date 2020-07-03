package quest;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * User class(player class).
 * the game object that the player will be controlling.
 *
 */
public class User extends MovableObject {

    /**
     * User class constructor.
     * sets up the sprite, tile and direction for the game object.
     *
     * @param sprite
     * @param tile
     * @param direction
     */
    public User(Image sprite, Tile tile, String direction) {
        super(sprite, tile, direction);
    }

    /**
     * handleKeyPressed.
     * handles the inputted key pressed by the user.
     * and moves the player character into the desired direction.
     *
     * @param code
     */
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
