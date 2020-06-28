package quest;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameOptions {

    private int x_tiles;
    private int y_tiles;
    private int tile_size;
    private int planet_count;
    private int comet_count;

    private Scene scene;

    public GameOptions(Main main) {
        this.x_tiles = 12;
        this.y_tiles = 12;
        this.tile_size = 50;
        this.planet_count = 3;
        this.comet_count = 5;

        this.constructScene(main);
    }

    public int getXTileCount() {
        return this.x_tiles;
    }

    public int getYTileCount() {
        return this.y_tiles;
    }

    public int getTileSize() {
        return this.tile_size;
    }

    public int getPlanetCount() {
        return this.planet_count;
    }

    public int getCometCount() {
        return this.comet_count;
    }

    public Scene getScene() {
        return this.scene;
    }

    private void constructScene(Main main) {
        Text title = new Text("Game Options");
        title.setFont(Main.FONT_130);

        Label x_tile_count_label = this.newLabel("Tiles in width: %d", this.x_tiles);
        Slider x_tile_count_slider = this.newSlider(12, 24, this.x_tiles, (e, o, n) ->
            this.x_tiles = this.updateLabel(x_tile_count_label, "Tiles in width: %d", n.intValue())
        );

        Label y_tile_count_label = this.newLabel("Tiles in height: %d", this.y_tiles);
        Slider y_tile_count_slider = this.newSlider(12, 24, this.y_tiles, (e, o, n) ->
            this.y_tiles = this.updateLabel(y_tile_count_label, "Tiles in height: %d", n.intValue())
        );

        Label tile_size_label = this.newLabel("Tile size: %d", this.tile_size);
        Slider tile_size_slider = this.newSlider(30, 60, this.tile_size, (e, o, n) ->
            this.tile_size = this.updateLabel(tile_size_label, "Tile size: %d", n.intValue())
        );

        Label planet_count_label = this.newLabel("Planets: %d", this.planet_count);
        Slider planet_count_slider = this.newSlider(3, 15, this.planet_count, (e, o, n) ->
            this.planet_count = this.updateLabel(planet_count_label, "Planets: %d", n.intValue())
        );

        Label comet_count_label = this.newLabel("Comets: %d", this.comet_count);
        Slider comet_count_slider = this.newSlider(5, 10, this.comet_count, (e, o, n) ->
            this.comet_count = this.updateLabel(comet_count_label, "Comets: %d", n.intValue())
        );

        Button save_and_go_back = new Button("Save and go back");
        save_and_go_back.setFont(Main.FONT_20);
        save_and_go_back.setMinSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        save_and_go_back.setOnAction(e -> main.gotoMenu());

        VBox options_box = new VBox(
                title,
                x_tile_count_label, x_tile_count_slider,
                y_tile_count_label, y_tile_count_slider,
                tile_size_label, tile_size_slider,
                planet_count_label, planet_count_slider,
                comet_count_label, comet_count_slider,
                save_and_go_back
        );
        options_box.setSpacing(10);
        options_box.setAlignment(Pos.CENTER);

        this.scene = new Scene(options_box, Main.STAGE_WIDTH, Main.STAGE_HEIGHT, Color.DARKGRAY);
    }

    private Label newLabel(String format, int value) {
        Label label = new Label(
                String.format(format, value)
        );
        label.setFont(Main.FONT_20);

        return label;
    }

    private Slider newSlider(int min, int max, int value, ChangeListener<Number> change_listener) {
        Slider slider = new Slider(min, max, value);
        slider.setMaxWidth(Main.STAGE_WIDTH * .3);

        if (change_listener != null) {
            slider.valueProperty().addListener(change_listener);
        }

        return slider;
    }

    private int updateLabel(Label label, String format, int value) {
        label.setText(
                String.format(format, value)
        );

        return value;
    }

}
