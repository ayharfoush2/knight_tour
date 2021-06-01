package boardgame;

/**
 * This is the figure class used to get and assign figures to the players
 */
public enum Figure {

    /**
     * The X figure.
     */
    W("W"),

    /**
*The O figure.
*/
 B("B");

    private final String figure;

    Figure(final String figureName) {
        figure = figureName;
    }

    @Override
    public String toString() {
        return figure;
    }

}