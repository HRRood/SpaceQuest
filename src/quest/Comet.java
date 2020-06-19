package quest;

import javafx.scene.image.Image;

public class Comet extends MovableObject {
    public Comet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    //updating Comet
    public void Update()
    {

        //get random location.
        int newPos = getRandom(1,4);

        //move
        setMove(newPos);

    }

    private int getRandom(int min, int max)
    {
        int temp = (int)(Math.random() * (max - min + 1) + min);
        return temp;
    }

}
