package boardgame.model;

import boardgame.exceptions.InvalidBoardSizeException;
import boardgame.Figure;
import boardgame.Player;
import boardgame.View.ConsoleView;

/**
 * the Application class
 */
public class BoardGameApplication {

    /**
     * Starting of the game
     * it checks if the board is visible based on the size requirement else throws invalidBoardSizeException.
     * @param BOARD_SIZE has to be 8
     * @param playerOneName player one name and it sets a figure to the player
     * @param playerTwoName player two name and it sets a figure(B or W) to the player
     * @param gameName sets the game name
     * @return the board if all requirements are met
     */
    public static BoardGameModel customStart(final int BOARD_SIZE, final String playerOneName, final String playerTwoName, final String gameName) {
        Board board = null;
        try {
            board = new Board(BOARD_SIZE);
        } catch (InvalidBoardSizeException e) {
            e.printStackTrace();
        }
        final Player[] players = new Player[2];
        players[0] = new Player(playerOneName, Figure.B);
        players[1] = new Player(playerTwoName, Figure.W);
        int steps = 0;
        final BoardGameController gameController = new BoardGameController(gameName, players, board);
        final ConsoleView consoleView = new ConsoleView(gameController);
        final BoardGameModel game = new BoardGameModel(consoleView);
        consoleView.showPlayers();
        return game;
    }
}
