package main.java.Quest.Object;

public enum Direction {

    UP (0),

    RIGHT (90),

    DOWN (180),

    LEFT (270);

    private final int radiant;

    Direction(int radiant) {
        this.radiant = radiant;
    }

    public int getRadiant() {
        return this.radiant;
    }

}
