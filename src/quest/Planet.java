package quest;

import javafx.scene.image.Image;

public class Planet extends Object {

    private boolean visited = false;

    public Planet(Image unvisited_sprite, Tile tile) {
        super(unvisited_sprite, tile);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

}

// TODO Sprites from https://opengameart.org/content/20-planet-sprites
