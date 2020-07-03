package quest;

import javafx.scene.image.Image;

/**
 * Comet class.
 * Comets move in random direction after checking if the direction is available
 * to move in.
 *
 */
public class Comet extends MovableObject {

    private String[] POSITION = {"up", "left", "right", "down"};
    public int newPos = 0;

    /**
     * class constructor.
     *
     * @param sprite
     * @param tile
     */
    public Comet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    /**
     * Update function.
     * loops until the comet has an available direction to move to.
     */
    //updating Comet
    public void update() {
        //get random location.
        boolean loop_runing = true;

        while (loop_runing) {
            newPos = getRandom();
            if(checkDirectionAvailable(newPos))
            {
                loop_runing = getCollision(this.getTile().getNeighbours().get(newPos)) == null;
                Game.game_over = !loop_runing;
            } else {
                loop_runing = false;
            }

        }

        //move
        moveObject(POSITION[newPos]);

    }

    /**
     * checkDirectionAvailable.
     * Checks if the parameter direction is available,
     * give back an true or false depending on the direction.
     *
     * @param dir
     * @return
     */
    public boolean checkDirectionAvailable(int dir) {
        if (this.getTile().getNeighbours().get(newPos).isAvailable()){
            return false;
        }else{
            return true;
        }
    }

    /**
     * getRandom.
     *  returns an random number between 1 and 4 to get an direction.
     * @return
     */
    private int getRandom() {
        return (int)(Math.random() * (3 + 1) + 0);
    }

}
