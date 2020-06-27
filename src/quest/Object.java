package quest;

import javafx.scene.image.Image;

public class Object {
    private Image sprite;
    private Tile tile;

    public Object(Image sprite, Tile tile) {
        this.sprite = sprite;
        this.tile = tile;
    }

    public Image getSprite() {
        return this.sprite;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
