package boardgame;


import boardgame.View.ConsoleMenuView;

import java.util.logging.Logger;

/**
 * This is the main class.
 */
public class Main {


    /**
     * The static main class for the game launch.
     * An access modifier
     * @param args .
     * @throws Exception if an error occurs
     */
    public static void main(String[] args) throws Exception {

        Logger logger
                = Logger.getLogger(
                Main.class.getName());

        logger.info("Game Loading ....");
        ConsoleMenuView.showMenuWithResult();


    }
}









