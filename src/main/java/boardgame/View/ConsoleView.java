package boardgame.View;

import boardgame.*;
import boardgame.database.Database;
import boardgame.database.Game;
import boardgame.database.GameDao;
import boardgame.exceptions.InvalidPointException;
import boardgame.model.BoardGameController;
import boardgame.model.Point;
import lombok.*;
import org.tinylog.Logger;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 *
 *   The console view class.
 *
 */
@Data


public class ConsoleView implements View {
    private static final String CHARACTER_HYPHEN = "-";

    private static final String EMPTY_FIGURE = " ";

    private static final String INPUT_ERROR = "Incorrect input, please try again";

    /**
     * Protected methods from Board game controller class to not exposing its members to the public.
     */
    protected final BoardGameController game;
    /**
     * Used to count the steps of players.
     */
    public int count = 0;
    /**
     * Used to set the scores of players.
     */
    public int score = 0;
    /**
     * Used to mark the start time of the game.
     */
    private static LocalTime startTime;
    /**
     * Indicates the names of players.
     */
    private String name;

    /**
     * The console view.
     *
     * @param game .
     */
    public ConsoleView(final BoardGameController game) {
        assert game != null;
        this.game = game;
    }

    /**
     * Gets the game controller and
     *
     * @return the game.
     */
    public BoardGameController getBoardGameController() {
        return game;
    }

    /**
     * At the start of the game it will indicate the turns.
     *
     * @return the new points or coordinates.
     */
    public Point startTurn() {
        startTime = LocalTime.now();
        for (Player player : game.getPlayers()) {
            count++;

            System.out.println("Next turn!");
            return new Point(getCoordinate(), getCoordinate());
        }
        return null;
    }

    /**
     * Shows each player, name and figure.
     */
    public void showPlayers() {
        for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + ": " + player.getFigure().toString());
        }
    }

    /**
     * This shows the board on the console.
     */
    public void showBoard() {
        int lineSize = game.getBoard().getFiguresArray().length;
        for (int i = 0; i < lineSize; i++) {
            showBoardLine(i);
            printLine(lineSize);
        }
    }

    /**
     * Shows winners name , figure , score and steps taken by both players.
     */
    public void showWinner() {

        for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + ": " + player.getFigure().toString());
        }
        score++;
        String winner = game.getWinner().getName().equals(game.getPlayers()[0].getName()) ? game.getPlayers()[1].getName() : game.getPlayers()[0].getName();
        Game existingGame = Database.jdbi.withExtension(GameDao.class, dao -> dao.checkIfPlayerExist(winner));
        if (existingGame != null)
            Database.persistGame(Game.builder()
                    .firstPlayerName(game.getPlayers()[0].getName())
                    .secondPlayerName(game.getPlayers()[1].getName())
                    .startTime(startTime)
                    .steps(count)
                    .date(LocalDate.now())
                    .winnerScore(score)
                    .winner(winner)
                    .build());
        else
            Database.jdbi.withExtension(GameDao.class, dao -> {
                dao.updatePlayerScore(existingGame.getWinner(), existingGame.getWinnerScore() + 1);
                return 1;
            });
        System.out.println("The Winner is : " + winner + " Score " + score);
        System.out.println("Steps : " + count);
        Logger.info("Game has been won");
        List<Game> topFive = Database.jdbi.withExtension(GameDao.class, GameDao::getTopFiveGames);
        System.out.println(topFive);

    }

    /**
     * Allows users to play another game
     *
     * @throws Exception if the input is not y or n
     */
    public void anotherGame() throws Exception {
        System.out.println("Want another game? Press y/n");
        try {
            final Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            switch (choice) {
                case "y":
                    Logger.info("Game Starting Again");
                    ConsoleMenuView.showMenuWithResult();
                    startTime = LocalTime.now();
                    break;
                case "n":
                    System.out.println("Exit...");
                    Logger.info("Game Ended");
                    System.exit(0);

                    break;
                default:
                    System.out.println("Please enter \"y\" or \"n\"");
                    anotherGame();
                    break;
            }
        } catch (final InputMismatchException e) {
            System.out.println("Please enter \"y\" or \"n\"");
            anotherGame();
        }
    }

    /**
     * Shows if the point is occupied
     */
    public void showPointOccupied() {
        System.out.println("Point already occupied!");
    }

    /**
     * it prints out the line
     *
     * @param lineSize the line size
     */
    private void printLine(final int lineSize) {
        for (int i = 0; i < lineSize; i++) {
            System.out.print(ConsoleView.CHARACTER_HYPHEN);
        }
        System.out.println();
    }

    /**
     * @param row --
     */


    private void showBoardLine(final int row) {
        for (int i = 0; i < game.getBoard().getRowLength(row); i++) {
            try {
                if (game.getBoard().getFigure(row, i) == null) {
                    System.out.print(EMPTY_FIGURE);
                } else {
                    System.out.print(game.getBoard().getFigure(row, i).toString());
                }
            } catch (final InvalidPointException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    /**
     * gets the coordinate based on the input by the player
     *
     * @return coordinate or if the coordinate is not valid , it will throw InputMismatchException
     */
    protected int getCoordinate() {
        while (true) {
            System.out.print("Input the coordinate ");
            try {
                final Scanner in = new Scanner(System.in);
                int coordinate = in.nextInt() - 1;
                if (game.getBoard().checkCoordinate(coordinate)) {

                    return coordinate;

                } else {
                    System.out.println(INPUT_ERROR);
                }
            } catch (final InputMismatchException e) {
                System.out.println(INPUT_ERROR);
            }


        }


    }


}

