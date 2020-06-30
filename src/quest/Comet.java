package quest;

import javafx.scene.image.Image;

public class Comet extends MovableObject {

    private String[] POSITION = {"up", "left", "right", "down"};
    public int newPos = 0;

    public Comet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

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

    public boolean checkDirectionAvailable(int dir) {
        if (this.getTile().getNeighbours().get(newPos).isAvailable()){
            return false;
        }else{
            return true;
        }
    }

    private int getRandom() {
        return (int)(Math.random() * (3 + 1) + 0);
    }

}
