package quest;

import javafx.scene.image.Image;
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

    public void moveObject(String move_to) {
        Tile object_tile = getTile();
        Object collided = null;
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
                } else {
                    collided = getCollision(neighbours.get(0));
                }
                break;
            }
            case "left": {
                if (neighbours.get(1).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(1).setObject(this);
                    setTile(neighbours.get(1));
                } else {
                    collided = getCollision(neighbours.get(1));
                }
                break;
            }
            case "right": {
                if (neighbours.get(2).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(2).setObject(this);
                    setTile(neighbours.get(2));
                } else {
                    collided = getCollision(neighbours.get(2));
                }
                break;
            }
            case "down": {
                if (neighbours.get(3).isAvailable()) {
                    object_tile.emptyTile();
                    neighbours.get(3).setObject(this);
                    setTile(neighbours.get(3));
                } else {
                    collided = getCollision(neighbours.get(3));
                }
                break;
            }
            default: {
                object_tile.setObject(this);
                setTile(object_tile);
                break;
            }
        }

        if (collided != null) {
            if (collided instanceof Comet || collided instanceof User) {
                Game.game_over = true;
            }

            if (collided instanceof Planet) {
                ((Planet) collided).setVisited();
            }

            if (collided instanceof Wormhole) {
                Game.game_won = true;
            }
        }
        this.direction = move_to;
    }

    public Object getCollision (Tile go_to_tile) {
        Object[] go_to_tile_object = go_to_tile.getObject();

        if (this instanceof User) {
            for (Object object : go_to_tile_object) {
                if (object instanceof Comet) {
                    return object;
                }

                if (object instanceof Planet) {
                    this.getTile().emptyTile();
                    go_to_tile.setObject(this);
                    this.setTile(go_to_tile);
                    return object;
                }

                if (object instanceof Wormhole) {
                    this.getTile().emptyTile();
                    this.setTile(go_to_tile);
                    return object;
                }
            }
        }

        if (this instanceof Comet) {
            if (go_to_tile_object[0] instanceof User) {
                return go_to_tile_object[0];
            }
        }
        return null;
    }
}
