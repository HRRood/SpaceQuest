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

/**
 * GameOptions.
 * The class in which the user can set up and change the game default options.
 */
public class GameOptions {

    private int x_tiles;
    private int y_tiles;
    private int tile_size;
    private int planet_count;
    private int comet_count;

    private Scene scene;

    /**
     * GameOptions.
     * the Gameoptions class constructor.
     * initializes the default options for the game.
     *
     * @param main
     */
    public GameOptions(Main main) {
        this.x_tiles = 12;
        this.y_tiles = 12;
        this.tile_size = 50;
        this.planet_count = 3;
        this.comet_count = 5;

        this.constructScene(main);
    }

    /**
     * GetXTileCount.
     * returns variable x_tiles.
     *
     * @return
     */
    public int getXTileCount() {
        return this.x_tiles;
    }

    /**
     * GetYTileCount.
     * returns variable y_tiles.
     *
     * @return
     */
    public int getYTileCount() {
        return this.y_tiles;
    }

    /**
     * GetTilesSize.
     * returns variable tile_size.
     *
     * @return
     */
    public int getTileSize() {
        return this.tile_size;
    }

    /**
     * GetPlanetCount.
     * returns variable planet_count.
     *
     * @return
     */
    public int getPlanetCount() {
        return this.planet_count;
    }

    /**
     * GetCometCount.
     * returns variable comet_count.
     *
     * @return
     */
    public int getCometCount() {
        return this.comet_count;
    }

    /**
     * GetScene.
     * returns the scene of the class.
     *
     * @return
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * constructScene.
     * initializes the scene for the game options.
     *
     * @param main
     */
    private void constructScene(Main main) {
        Text title = new Text("Game Options");
        title.setFont(Main.FONT_130);

        Label x_tile_count_label = this.newLabel("Tiles in width: %d", this.x_tiles);
        Slider x_tile_count_slider = this.newSlider(12, 20, this.x_tiles, (observable, previous_value, new_value) ->
            this.x_tiles = this.updateLabel(x_tile_count_label, "Tiles in width: %d", new_value.intValue())
        );

        Label y_tile_count_label = this.newLabel("Tiles in height: %d", this.y_tiles);
        Slider y_tile_count_slider = this.newSlider(12, 20, this.y_tiles, (observable, previous_value, new_value) ->
            this.y_tiles = this.updateLabel(y_tile_count_label, "Tiles in height: %d", new_value.intValue())
        );

        Label tile_size_label = this.newLabel("Tile size: %d", this.tile_size);
        Slider tile_size_slider = this.newSlider(30, 60, this.tile_size, (observable, previous_value, new_value) ->
            this.tile_size = this.updateLabel(tile_size_label, "Tile size: %d", new_value.intValue())
        );

        Label planet_count_label = this.newLabel("Planets: %d", this.planet_count);
        Slider planet_count_slider = this.newSlider(3, 15, this.planet_count, (observable, previous_value, new_value) ->
            this.planet_count = this.updateLabel(planet_count_label, "Planets: %d", new_value.intValue())
        );

        Label comet_count_label = this.newLabel("Comets: %d", this.comet_count);
        Slider comet_count_slider = this.newSlider(5, 10, this.comet_count, (observable, previous_value, new_value) ->
            this.comet_count = this.updateLabel(comet_count_label, "Comets: %d", new_value.intValue())
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

    /**
     * NewLabel.
     * makes an Label with the variables from the parameters.
     *
     * @param format
     * @param value
     * @return
     */
    private Label newLabel(String format, int value) {
        Label label = new Label(String.format(format, value));
        label.setFont(Main.FONT_20);

        return label;
    }

    /**
     * NewSlider.
     * makes an slider with the variables from the parameters.
     *
     * @param min
     * @param max
     * @param value
     * @param change_listener
     * @return
     */
    private Slider newSlider(int min, int max, int value, ChangeListener<Number> change_listener) {
        Slider slider = new Slider(min, max, value);
        slider.setMaxWidth(Main.STAGE_WIDTH * .3);

        if (change_listener != null) {
            slider.valueProperty().addListener(change_listener);
        }

        return slider;
    }

    /**
     * UpdateLabel.
     * updates an parameter given label with new variables.
     *
     * @param label
     * @param format
     * @param value
     * @return
     */
    private int updateLabel(Label label, String format, int value) {
        label.setText(String.format(format, value));

        return value;
    }

}
