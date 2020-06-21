package quest;

public class GameOptions {

    private int tiles;
    private int planets;
    private int comets;

    public GameOptions() {
        this.tiles = 12;
        this.planets = 3;
        this.comets = 5;
    }

    public GameOptions(int tiles, int planets, int comets) {
        this.tiles = tiles;
        this.planets = planets;
        this.comets = comets;
    }

    public int getTileCount() {
        return this.tiles;
    }

    public void setTileCount(int tiles) {
        this.tiles = tiles;
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
