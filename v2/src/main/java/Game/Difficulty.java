package main.java.Game;

public enum Difficulty {

    EASY (12, 12, 5, 3, 0),
    MEDIUM (18, 18, 10, 6, 1),
    HARD (24, 24, 15, 9, 2),
    ULTRA (30, 30, 20, 12, 3);

    public final int xTileCount;
    public final int yTileCount;
    public final int planetCount;
    public final int cometCount;
    public final int spacePirateCount;

    Difficulty(int xTileCount, int yTileCount, int planetCount, int cometCount, int spacePirateCount) {
        this.xTileCount = xTileCount;
        this.yTileCount = yTileCount;
        this.planetCount = planetCount;
        this.cometCount = cometCount;
        this.spacePirateCount = spacePirateCount;
    }

}
