package quest;

import javafx.scene.image.Image;

public class Planet extends Object {

    private final Image visited_sprite;
    private boolean visited = false;

    public Planet(Image unvisited_sprite, Image visited_sprite, Tile tile) {
        super(unvisited_sprite, tile);

        this.visited_sprite = visited_sprite;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited() {
        this.setSprite(this.visited_sprite);
        this.visited = true;
    }

}

// TODO Sprites from https://opengameart.org/content/20-planet-sprites
