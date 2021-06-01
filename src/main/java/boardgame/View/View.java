package boardgame.View;


import boardgame.model.BoardGameController;
import boardgame.model.Point;

/**
 * This is the interface class
 */
public interface View {

    /**
     * When the game is Launched.
     * @return First Turn.
     */
    Point startTurn();

    /**
     * Shows Players.
     */
    void showPlayers();

    /**
     * Shows Board.
     */
    void showBoard();

    /**
     * Shows winner player.
     */
    void showWinner();


    /**
     * Gives the play again option.
     * @throws Exception if the character chosen is not y or n.
     */
    void anotherGame() throws Exception;

    /**
     * Shows when a point is already occupied by another player.
     */
    void showPointOccupied();

    /**
     * @return The board game Controller class
     */
    BoardGameController getBoardGameController();

}
