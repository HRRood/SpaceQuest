package quest;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * Tile class.
 * the class of which the grid has been created
 * and all game object move maneuver.
 */
public class Tile {
    private int position_x;
    private int position_y;
    private int size;
    private boolean isAvailable = true;
    private Image background;

    private List<Tile> neighbours;
    private Object[] object = new Object[2];

    private ImageView object_view;
    private ImageView top_user_view;
    private StackPane pane;
    private Rectangle rect;

    /**
     * Tile class constructor.
     * sets up the tile object. the position, size, background image
     * and if it is available yes or no.
     *
     * @param x
     * @param y
     * @param size
     * @param background
     */
    public Tile(int x, int y, int size, Image background) {
        this.position_x = x;
        this.position_y = y;
        this.size = size;
        this.background = background;

        this.pane = new StackPane();
        this.rect = new Rectangle(this.size + 1, this.size + 1);

        if (this.position_x == -1 || this.position_y == -1) {
            this.isAvailable = false;
        }

        this.createTile();
    }

    /**
     * CreateTile.
     * creates the tile with the class variables,
     * that are set up by the constructor.
     *
     */
    private void createTile() {
        if(this.position_y < 0 && this.position_x < 0) {
            return;
        }

        this.rect.setFill(new ImagePattern(this.background));
        this.rect.setStroke(Color.TRANSPARENT);
        this.rect.setTranslateX(this.position_x * this.size);
        this.rect.setTranslateY(this.position_y * this.size);
        this.pane.getChildren().add(this.rect);
    }

    /**
     * GetPane.
     * calls the updateObject function
     * and returns the pane of the object.
     *
     * @return
     */
    public StackPane getPane() {
        this.updateObject();
        return pane;
    }

    /**
     * UpdateObject.
     * Updates the tile, removes all children and puts them back up with updated
     * variables.
     */
    public void updateObject () {

        this.pane.getChildren().removeAll(this.object_view, this.top_user_view);
        if (this.object[0] == null) {
            return;
        }

        this.object_view = new ImageView(this.object[0].getSprite());
        this.object_view.setFitWidth(this.size * 0.8);
        this.object_view.setFitHeight(this.size * 0.8);
        this.object_view.setTranslateX(this.position_x * this.size);
        this.object_view.setTranslateY(this.position_y * this.size);

        if (this.object[0] instanceof MovableObject) {
            this.object_view.setRotate(((MovableObject) this.object[0]).getDirection());
        }

        if (this.object[0] instanceof Planet && ((Planet) this.object[0]).isVisited()) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5);

            object_view.setEffect(colorAdjust);
        }

        this.pane.getChildren().add(object_view);


        if (this.object[1] != null && this.object[1] instanceof User) {
            this.top_user_view = new ImageView(this.object[1].getSprite());
            this.top_user_view.setFitWidth(this.size * 0.8);
            this.top_user_view.setFitHeight(this.size * 0.8);
            this.top_user_view.setTranslateX(this.position_x * this.size);
            this.top_user_view.setTranslateY(this.position_y * this.size);
            this.top_user_view.setRotate(((MovableObject) this.object[1]).getDirection());
            this.pane.getChildren().add(this.top_user_view);
        }
    }

    /**
     * SetNeighbours.
     * puts the given parameter list into the object neighbours array.
     * sets all neighbouring tiles in the array.
     *
     * @param neighbours
     */
    public void setNeighbours(List<Tile> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * getNeighbours.
     * returns the neighbours arrayList variable.
     *
     * @return
     */
    public List<Tile> getNeighbours() {
        return neighbours;
    }

    /**
     * getPosition_X.
     * return variable position_x.
     *
     * @return
     */
    public int getPosition_x() {
        return position_x;
    }

    /**
     * getPosition_Y.
     * return variable position_y.
     *
     * @return
     */
    public int getPosition_y() {
        return position_y;
    }

    /**
     * isAvailable.
     * returns variable isAvailable.
     *
     * @return
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * SetObject.
     * set the object that is given with the parameter
     * as the object that is on top of this tile.
     * sets availability on false.
     * @param object
     */
    public void setObject(Object object) {
        if (this.object[0] == null) {
            this.object[0] = object;
            this.isAvailable = false;
        }
        if (this.object[0] instanceof Planet && object instanceof User) {
            this.object[1] = object;
        }
    }

    /**
     * emptyTile.
     * empties the tile.
     * and sets availability back to true.
     */
    public void emptyTile() {
        if (this.object[1] != null) {
            this.object[1] = null;
        } else {
            this.object[0] = null;
            this.isAvailable = true;
        }
    }

    /**
     * getObject.
     * return the object that is current on top of the tile.
     *
     * @return
     */
    public Object[] getObject () {
        return this.object;
    }
}
