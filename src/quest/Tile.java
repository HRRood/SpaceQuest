package quest;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.List;

public class Tile {
    private int position_x;
    private int position_y;
    private int size;
    private boolean isAvailable = true;
    private Image background;

    private List<Tile> neighbours;
    private Object[] object = new Object[2];
    private ImageView objectview;
    private ImageView top_user_view;

    private StackPane pane;
    private Rectangle rect;

    public Tile(int x, int y, int size, Image background) {
        this.position_x = x;
        this.position_y = y;
        this.size = size;
        this.background = background;

        this.pane = new StackPane();
        this.rect = new Rectangle(this.size + 1, this.size + 1);

        if (position_x == -1 || position_y == -1) {
            isAvailable = false;
        }
        creatTile();
    }

    private void creatTile(){
        if(this.position_y < 0 && this.position_x < 0) {
            return;
        }
        rect.setFill(new ImagePattern(this.background));
        rect.setStroke(Color.TRANSPARENT);
        rect.setTranslateX(position_x * this.size);
        rect.setTranslateY(position_y * this.size);
        this.pane.getChildren().add(rect);
    }

    public StackPane getPane() {
        updateObject();
        return pane;
    }

    public void updateObject () {
        this.pane.getChildren().removeAll(objectview, top_user_view);
        if (object[0] == null) {
            return;
        }

        objectview = new ImageView(object[0].getSprite());
        objectview.setFitWidth(this.size * 0.8);
        objectview.setFitHeight(this.size * 0.8);
        objectview.setTranslateX(position_x * this.size);
        objectview.setTranslateY(position_y * this.size);

        if (object[0] instanceof MovableObject) {
            objectview.setRotate(((MovableObject) object[0]).getDirection());
        }

        if (object[0] instanceof Planet && ((Planet) object[0]).isVisited()) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5);
            objectview.setEffect(colorAdjust);
        }

        this.pane.getChildren().add(objectview);

        if (object[1] != null && object[1] instanceof User) {
            top_user_view = new ImageView(object[1].getSprite());
            top_user_view.setFitWidth(this.size * 0.8);
            top_user_view.setFitHeight(this.size * 0.8);
            top_user_view.setTranslateX(position_x * this.size);
            top_user_view.setTranslateY(position_y * this.size);
            top_user_view.setRotate(((MovableObject) object[1]).getDirection());
            this.pane.getChildren().add(top_user_view);
        }
    }

    public void setNeighbours(List<Tile> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Tile> getNeighbours() {
        return neighbours;
    }

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setObject(Object object) {
        isAvailable = false;
        if (this.object[0] == null) {
            this.object[0] = object;
        }
        if (this.object[0] instanceof Planet && object instanceof User) {
            this.object[1] = object;
        }
    }

    public void emptyTile() {
        if (this.object[1] != null) {
            this.object[1] = null;
        } else {
            this.object[0] = null;
            isAvailable = true;
        }
    }

    public Object[] getObject () {
        return this.object;
    }
}
