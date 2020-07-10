package main.java.Quest.Object;

import javafx.scene.image.Image;
import main.java.Quest.Tile;

public class MovableObject extends Object {

    public Direction direction;

    public MovableObject(Image sprite, Tile tile) {
        super(sprite, tile);
    }

    public MovableObject(Image sprite, Tile tile, Direction direction) {
        super(sprite, tile);
        this.direction = direction;
    }

    public CollusionType moveObjectOK(Direction direction) {
        this.direction = direction;

        switch (direction) {
            case UP:
                this.move(this.tile.neighbours.top);
                break;
            case RIGHT:
                this.move(this.tile.neighbours.right);
                break;
            case DOWN:
                this.move(this.tile.neighbours.bottom);
                break;
            case LEFT:
                this.move(this.tile.neighbours.left);
                break;
        }

        return this.checkCollusion();
    }

    private void move(Tile newTile) {
        if (newTile == null) {
            return;
        }

        if (this instanceof Comet) {
            this.tile.objects.comet = null;
            this.tile = newTile;
            newTile.objects.comet = (Comet) this;
        }

        if (this instanceof SpacePirate) {
            this.tile.objects.spacePirate = null;
            this.tile = newTile;
            newTile.objects.spacePirate = (SpacePirate) this;
        }

        if (this instanceof SpaceShip) {
            this.tile.objects.spaceShip = null;
            this.tile = newTile;
            newTile.objects.spaceShip = (SpaceShip) this;
        }
    }

    private CollusionType checkCollusion() {
        if (this instanceof SpaceShip) {
            if (this.tile.objects.comet != null) {
                return CollusionType.COMET;
            } else if (this.tile.objects.planet != null) {
                this.tile.objects.planet.visited = true;
            } else if (this.tile.objects.wormhole != null) {
                return CollusionType.WORMHOLE;
            } else if (this.tile.objects.spacePirate != null) {
                return CollusionType.SPACEPIRATE;
            }
        }

        if (this instanceof Comet) {
            if (this.tile.objects.planet != null) {
                System.out.println("comet hits planet");
            } else if (this.tile.objects.wormhole != null) {
                System.out.println("comet hits wormhole");
            } else if (this.tile.objects.spaceShip != null) {
                System.out.println("comet hits spaceShip");
            } else if (this.tile.objects.spacePirate != null) {
                System.out.println("comet hits spacePirate");
            }

        }

        if (this instanceof SpacePirate) {
            // System.out.println("spacePirate collusion");
        }

        return CollusionType.IGNORE;
    }

}
