package quest;

import javafx.scene.image.Image;

public class Planet extends Object {

    private boolean visited = false;

    public Planet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

}