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
        boolean loop_running = true;
        while (loop_running) {
            newPos = getRandom();
            if (!this.getTile().getNeighbours().get(newPos).isAvailable()) {
                loop_running = getCollision(this.getTile().getNeighbours().get(newPos)) == null;
                Main.game_over = !loop_running;
            } else {
                loop_running = false;
            }
        }

        //move
        moveObject(POSITION[newPos]);

    }

    private int getRandom() {
        return (int)(Math.random() * (3 + 1) + 0);
    }

}

