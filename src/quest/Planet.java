package quest;

import javafx.scene.image.Image;

/**
 * Plant Class.
 * the planet game object class.
 * these are the objectives that the player needs to visit before winning the game.
 */
public class Planet extends Object {

    private boolean visited = false;

    /**
     * Planet class constructor.
     * sets up the sprite and tile for the planet object.
     *
     * @param sprite
     * @param tile
     */
    public Planet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    /**
     * isVisited.
     * returns a boolean visited.
     * if the player has visited the planet it will be true else it is false.
     *
     * @return
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * SetVisited.
     * sets the boolean visited to true.
     */
    public void setVisited() {
        this.visited = true;
    }

}