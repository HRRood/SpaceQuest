package quest;

import javafx.scene.image.Image;

public class Comet extends MovableObject {
    String[] POSITION = {"up", "left", "right", "down"};
    public Comet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    //updating Comet
    public void update()
    {

        //get random location.
        int newPos = getRandom();
        while (!this.getTile().getNeighbours().get(newPos).isAvailable()) {
            newPos = getRandom();
        }

        //move
        moveObject(POSITION[newPos]);

    }

    private int getRandom()
    {
        return (int)(Math.random() * (3 + 1) + 0);
    }

}

