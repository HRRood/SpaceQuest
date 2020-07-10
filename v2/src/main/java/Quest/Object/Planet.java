package main.java.Quest.Object;

import javafx.scene.image.Image;
import main.java.Quest.Tile;

public class Planet extends Object {

    public boolean visited = false;

    public Planet(Image sprite, Tile tile) {
        super(sprite, tile);
    }

}
