package main.java.Game;

public enum Difficulty {

    EASY (12, 5, 3, 0, 24),
    MEDIUM (18, 10, 6, 1, 36),
    HARD (24, 15, 9, 2, 48),
    ULTRA (30, 20, 12, 3, 60);

    public final int tileCount;
    public final int planetCount;
    public final int cometCount;
    public final int spacePirateCount;
    public final int timeCount;

    Difficulty(int tileCount, int planetCount, int cometCount, int spacePirateCount, int timeCount) {
        this.tileCount = tileCount;
        this.planetCount = planetCount;
        this.cometCount = cometCount;
        this.spacePirateCount = spacePirateCount;
        this.timeCount = timeCount;
    }

}
