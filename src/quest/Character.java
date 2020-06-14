package quest;

import javafx.scene.image.Image;

public class Character extends Object {
    private String direction;
    public Character(Image sprite, Tile tile, String direction) {
        super(sprite, tile);
        this.direction = direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getDirection() {
        if (direction == null) {
            return 0;
        }
        switch (direction) {
            case "right": {
                return 90;
            }
            case "down": {
                return 180;
            }
            case "left": {
                return 270;
            }
            default: {
                return 0;
            }
        }
    }
}
