package quest;

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
    private boolean isAvailable = true;
    private Image background;

    private List<Tile> neighbours;

    private User user;
    private Object object;
    private ImageView objectview;

    private StackPane pane = new StackPane();
    private Rectangle rect = new Rectangle(Main.TILE_SIZE+1, Main.TILE_SIZE+1);


    public Tile(int x, int y, Image background) {
        this.position_x = x;
        this.position_y = y;
        this.background = background;

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
        rect.setTranslateX(position_x * Main.TILE_SIZE);
        rect.setTranslateY(position_y * Main.TILE_SIZE);
        this.pane.getChildren().add(rect);
    }

    public StackPane getPane() {
        updateObject();
        return pane;
    }

    public void updateObject () {
        this.pane.getChildren().remove(objectview);
        if (object == null) {
            return;
        }

        objectview = new ImageView(object.getSprite());
        objectview.setFitWidth(Main.TILE_SIZE * 0.8);
        objectview.setFitHeight(Main.TILE_SIZE * 0.8);
        objectview.setTranslateX(position_x * Main.TILE_SIZE);
        objectview.setTranslateY(position_y * Main.TILE_SIZE);
        if (object instanceof MovableObject) {
            objectview.setRotate(((MovableObject) object).getDirection());
        }
        this.pane.getChildren().add(objectview);
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
        this.object = object;
        isAvailable = false;
    }

    public void emptyTile() {
        this.object = null;
        isAvailable = true;
    }

    public Object getObject () {
        return this.object;
    }
}
