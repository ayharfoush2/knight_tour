package boardgame.View;

import boardgame.model.BoardGameApplication;
import boardgame.exceptions.InvalidBoardSizeException;
import boardgame.model.BoardGameModel;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.tinylog.Logger;


/**
 * The console menu class
 * it gives the option to start a game or exit
 */
public class ConsoleMenuView {


    private static final int START_CODE = 1;

    private static final int EXIT_CODE = 2;

    private static int BOARD_SIZE;

    private static final int MIN_SIZE = 5;
    private static Instant startTime;

    /**
     * This class shows the menu and the right input for each option
     * @throws Exception if the input doe not correspond with the suggested input
     */
    public static void showMenuWithResult() throws Exception {



        System.out.println("Board Game");
        System.out.println(START_CODE + " - Play");
        System.out.println(EXIT_CODE + " - Exit");
        System.out.print("> ");

        try {
            final Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case START_CODE:
                    startTime = Instant.now();
                    Logger.info("Game Starting");
                    System.out.println("A new game started" + " " + startTime);
                    customInput().theBoardGameModel();
                    break;
                case EXIT_CODE:
                    System.out.println("Exit");
                    Logger.info("Game Exiting");
                    break;
                default:
                    System.out.println("incorrect, please try again");
                    showMenuWithResult();
                    break;
            }
        } catch (final InputMismatchException e) {
            System.out.println("Please enter again");
            showMenuWithResult();
        }

    }

    /**
     * Asks for the players name.
     * @return board if all names are entered
     */
    protected static BoardGameModel customInput()  {

        Scanner input = new Scanner(System.in);
        final String gameName = "Board Game";
        System.out.println("Enter player1 name:");
        String playerOneName = input.nextLine();
        Logger.info("Player1 name added ");
        System.out.println("Enter player2 name:");
        String playerTwoName = input.nextLine();
        Logger.info("Player2 name added ");
        final int boardSize = enterSize();
        return BoardGameApplication.customStart(boardSize, playerOneName, playerTwoName, gameName);
    }

    /**
     * Allows and ask players to enter the board size (8)
     * @return the board if the entered size is 8 or throws InvalidBoardSizeException if its greater or less than the 8
     */
    protected static int enterSize() {

        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter board size:");
            BOARD_SIZE = input.nextInt();
            Logger.info("Board size added GoodLuck");
            if (BOARD_SIZE < MIN_SIZE) {
                throw new InvalidBoardSizeException();
            }
        } catch (final InputMismatchException | InvalidBoardSizeException e) {
            System.out.println("Input is wrong, please enter correct integer 8");
            enterSize();
        }
        return BOARD_SIZE;
    }

}

