package quest;

import javafx.scene.image.Image;
import java.util.List;

/**
 * MovableObject Class.
 * the Parent class for all movable object in the game.
 * it is a child of the Object Class.
 */
public class MovableObject extends Object {

    private String direction;

    /**
     * MovableObject class constructor.
     * Sets up a movable object with an direction.
     *
     * @param sprite
     * @param tile
     * @param direction
     */
    public MovableObject(Image sprite, Tile tile, String direction) {
        super(sprite, tile);
        this.direction = direction;
    }

    /**
     * MovableObject class constructor.
     * Sets up a movable object with an direction.
     *
     * @param sprite
     * @param tile
     */
    public MovableObject(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    /**
     * GetDirection.
     * return a int for the rotation direction.
     *
     * @return
     */
    public int getDirection() {
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

    /**
     * moveObject.
     * Move the object to a desired direction.
     * get the tile of the object and the direction it wants to go to.
     * check if the tile in the direction available is.
     * if it is not check what on the tile.
     * if the object on the tile compable is witht this object
     * Move to it, else don't.
     *
     * @param move_to
     */
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
                this.direction = move_to;
            }

            if (collided instanceof Wormhole) {
                Game.game_won = true;
            }
        } else {
            this.direction = move_to;
        }
    }

    /**
     * getCollision.
     * check which object is on the desired tile to move to.
     * return the object on the tile else return null.
     *
     * @param go_to_tile
     * @return
     */
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
