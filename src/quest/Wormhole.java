package quest;

import javafx.scene.image.Image;

/**
 * Wormhole class.
 * the game object wormhole.
 * this is the last object that initializes and is placed in the game.
 * the player wins the game if it lands it the wormhole.
 */
public class Wormhole extends Object {

    /**
     * Wormhole class constructor.
     * sets up the sprite and tile for the wormhole.
     *
     * @param sprite
     * @param tile
     */
    public Wormhole(Image sprite, Tile tile) {
        super(sprite, tile);
    }

}
