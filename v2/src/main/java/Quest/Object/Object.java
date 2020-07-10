package main.java.Quest.Object;

import javafx.scene.image.Image;
import main.java.Quest.Tile;

public class Object {

    private final Image sprite;
    public Tile tile;

    public Object(Image sprite, Tile tile) {
        this.sprite = sprite;
        this.tile = tile;
    }

    public Image getSprite() {
        return this.sprite;
    }

}
