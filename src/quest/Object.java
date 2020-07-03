package quest;

import javafx.scene.image.Image;

/**
 * Object class.
 * the object class, parent class for every game object in the game.
 */
public class Object {

    private Image sprite;
    private Tile tile;

    /**
     * Object class constructor.
     * sets up the sprite and tile for the object.
     *
     * @param sprite
     * @param tile
     */
    public Object(Image sprite, Tile tile) {
        this.sprite = sprite;
        this.tile = tile;
    }

    /**
     * getSprite.
     * returns the sprite of the object.
     *
     * @return
     */
    public Image getSprite() {
        return this.sprite;
    }

    /**
     * GetTile.
     * returns the tile of the object.
     *
     * @return
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * setTile.
     * sets up the tile for the object.
     *
     * @param tile
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

}
