package quest;

import javafx.scene.image.Image;

public class Comet extends MovableObject {
    String[] POSITION = {"up", "left", "right", "down"};
    public Comet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    //updating Comet
    public void update() {

        //get random location.
        int newPos = getRandom();
        boolean loop_runing = true;
        while (loop_runing) {
            newPos = getRandom();
            if (!this.getTile().getNeighbours().get(newPos).isAvailable()) {
                loop_runing = getCollision(this.getTile().getNeighbours().get(newPos)) == null;
                Game.game_over = !loop_runing;
            } else {
                loop_runing = false;
            }
        }

        //move
        moveObject(POSITION[newPos]);

    }

    private int getRandom() {
        return (int)(Math.random() * (3 + 1) + 0);
    }

}

