package boardgame.model;

import boardgame.*;
import boardgame.exceptions.PointOccupiedException;
import boardgame.View.View;


/**
 * The Board game model
 */
public class BoardGameModel {
    final private View view;
    final private BoardGameController gameController;

    /**
     * @param view an interface class
     */
    public BoardGameModel(View view) {
        this.view = view;
        this.gameController = view.getBoardGameController();
    }

    /**
     * @throws Exception if the point chosen if occupied.
     */
    public void theBoardGameModel() throws Exception {
        while (gameController.getNextTurn()) {
            Point point = view.startTurn();
            try {
                Player currentPlayer = gameController.getCurrentPlayer(gameController.getPlayers()[0]);
                gameController.move(point.getX(), point.getY(), currentPlayer);
                gameController.togglePlayer();
            } catch (PointOccupiedException e) {
                view.showPointOccupied();
            }
            view.showBoard();
        }
        if (gameController.getWinner() != null) {
            view.showWinner();
            view.anotherGame();
        }
    }

}
