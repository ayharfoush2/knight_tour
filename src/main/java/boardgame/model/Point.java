package boardgame.model;

/**
 * marks the coordinate x and y
 */
public class Point {
    private final int x;
    private final int y;

    /**
     * @param x coordinate x.
     * @param y coordinate y.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Coordinate x.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Coordinate y.
     */
    public int getY() {
        return y;
    }
}
