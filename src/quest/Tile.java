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

        if (this.position_x == -1 || this.position_y == -1) {
            this.isAvailable = false;
        }

        this.creatTile();
    }

    private void creatTile() {
        if(this.position_y < 0 && this.position_x < 0) {
            return;
        }

        this.rect.setFill(new ImagePattern(this.background));
        this.rect.setStroke(Color.TRANSPARENT);
        this.rect.setTranslateX(this.position_x * this.size);
        this.rect.setTranslateY(this.position_y * this.size);
        this.pane.getChildren().add(this.rect);
    }

    public StackPane getPane() {
        this.updateObject();
        return pane;
    }

    public void updateObject () {
        this.pane.getChildren().removeAll(this.objectview, this.top_user_view);
        if (this.object[0] == null) {
            return;
        }

        this.objectview = new ImageView(this.object[0].getSprite());
        this.objectview.setFitWidth(this.size * 0.8);
        this.objectview.setFitHeight(this.size * 0.8);
        this.objectview.setTranslateX(this.position_x * this.size);
        this.objectview.setTranslateY(this.position_y * this.size);

        if (this.object[0] instanceof MovableObject) {
            this.objectview.setRotate(((MovableObject) this.object[0]).getDirection());
        }

        if (this.object[0] instanceof Planet && ((Planet) this.object[0]).isVisited()) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5);
            this.objectview.setEffect(colorAdjust);
        }

        this.pane.getChildren().add(this.objectview);

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
        if (this.object[0] == null) {
            this.object[0] = object;
            this.isAvailable = false;
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
            this.isAvailable = true;
        }
    }

    public Object[] getObject () {
        return this.object;
    }
}
