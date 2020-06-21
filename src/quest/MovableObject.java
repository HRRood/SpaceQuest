package quest;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.List;

public class MovableObject extends Object {
    private String direction;
    public MovableObject(Image sprite, Tile tile, String direction) {
        super(sprite, tile);
        this.direction = direction;
    }

    public MovableObject(Image sprite, Tile tile) {
        super(sprite, tile);
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

    public void handleKeyPressed (KeyCode code) {
        switch (code) {
            case W:
            case UP: {
                moveObject("up");
                break;
            }
            case S:
            case DOWN: {
                moveObject("down");
                break;
            }
            case A:
            case LEFT: {
                moveObject("left");
                break;
            }
            case D:
            case RIGHT: {
                moveObject("right");
                break;
            }
        }
    }

    public void moveObject(String move_to) {
        Tile object_tile = getTile();
        if (object_tile == null) {
            return;
        }
        List<Tile> neighbours = object_tile.getNeighbours();
        switch (move_to) {
            case "up": {
                if (neighbours.get(0).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(0).setObject(this);
                    setTile(neighbours.get(0));
                }
                break;
            }
            case "left": {
                if (neighbours.get(1).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(1).setObject(this);
                    setTile(neighbours.get(1));
                }
                break;
            }
            case "right": {
                if (neighbours.get(2).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(2).setObject(this);
                    setTile(neighbours.get(2));
                }
                break;
            }
            case "down": {
                if (neighbours.get(3).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(3).setObject(this);
                    setTile(neighbours.get(3));
                }
                break;
            }
            default: {
                object_tile.setObject(this);
                setTile(object_tile);
                break;
            }
        }
        this.direction = move_to;
    }
}
