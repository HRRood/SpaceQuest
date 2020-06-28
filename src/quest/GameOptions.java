package quest;

public class GameOptions {

    private int x_tiles;
    private int y_tiles;
    private int tile_size;
    private int planets;
    private int comets;

    public GameOptions() {
        this.x_tiles = 12;
        this.y_tiles = 12;
        this.tile_size = 50;
        this.planets = 3;
        this.comets = 5;
    }

    public int getXTileCount() {
        return this.x_tiles;
    }

    public void setXTileCount(int x_tiles) {
        this.x_tiles = x_tiles;
    }

    public int getYTileCount() {
        return this.y_tiles;
    }

    public void setYTileCount(int y_tiles) {
        this.y_tiles = y_tiles;
    }

    public int getTileSize() {
        return this.tile_size;
    }

    public void setTileSize(int tile_size) {
        this.tile_size = tile_size;
    }

    public int getPlanetCount() {
        return this.planets;
    }

    public void setPlanetCount(int planets) {
        this.planets = planets;
    }

    public int getCometCount() {
        return this.comets;
    }

    public void setCometCount(int comets) {
        this.comets = comets;
    }
}
