package boardgame.model;


import boardgame.exceptions.InvalidPointException;
import boardgame.exceptions.PointOccupiedException;
import boardgame.Figure;
import boardgame.Player;

/**
 * This is the Game Controller
 * It holds the rules of the game
 */
public class BoardGameController {

    private static final String Game_Name = "Knight Tour Game";
    private static final int Player1 = 0;
    private static final int Player2 = 1;
    private final String gameName;
    private final Player[] players;
    private final Board board;
    private final int boardLength;
    private static int currentPlayerNumber = 0;
    private static int player1LastPointX=-1;
    private static int player1LastPointY=-1;

    private static int player2LastPointX=-1;
    private static int player2LastPointY=-1;

    /**
     * The board game controller checks if the game name is null, if not assign the following:
     * @param gameName Game Name.
     * @param players  Players.
     * @param board    Board.
     */
    public BoardGameController(final String gameName, final Player[] players, final Board board) {
        if (gameName == null || gameName.isEmpty()) {
            this.gameName = Game_Name;

        } else {
            this.gameName = gameName;

        }
        this.board = board;
        this.players = players;
        this.boardLength = board.getFiguresArray().length;


    }

    /**
     * Gets the winner of the game
     *
     * @return winner if conditions for a win are met
     */
    
        public Player getWinner() {
        for (Player playerForCheck : players) {
            String figureToCheck = playerForCheck.getFigure().toString();
            if (CheckValidMoves(figureToCheck)) {
                return playerForCheck;

            }


        }
        return null;
    }

    /**
     * Gets game name
     *
     * @return game name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Gets players
     *
     * @return players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * gets board
     *
     * @return Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This Checks the moves
     *
     * @param x coordinate
     * @param y coordinate
     * @param player ( 1 or 2)
     * @throws PointOccupiedException if coordinate chosen is not in the coordinates
     */
    public void move(final int x, final int y, final Player player) throws PointOccupiedException {
        try {
            if (board.getFigure(x, y) != null) {
                throw new PointOccupiedException();

            } else {
                try {

                    if(checkPossibleMove(x,y)){
                        board.setFigure(x, y, player.getFigure());
                    }else{
                        try{
                            togglePlayer();
                            throw new InvalidPointException();
                        }catch (InvalidPointException e) {
                            e.printStackTrace();
                        }

                    }
                    if (currentPlayerNumber == Player1){
                        player1LastPointX = x;
                        player1LastPointY = y;
                    }else{
                        player2LastPointX = x;
                        player2LastPointY = y;
                    }


                } catch (InvalidPointException e) {
                    e.printStackTrace();
                }
            }
        } catch (InvalidPointException e) {
            e.printStackTrace();
        }

    }
    private boolean CheckValidMoves(final String playerFigure){
        int X[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int Y[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int newX = 0;
        int newY = 0;
        int count = 0;
        for(int i=0; i< 8;i++){
            if(currentPlayerNumber == Player1){
                newX = player1LastPointX + X[i];
                newY = player1LastPointY + Y[i];
            }else{
                newX = player2LastPointX + X[i];
                newY = player2LastPointY + Y[i];
            }
            if(newX>=0 && newY>=0 && newX<8 && newY<8){
                count++;
            }
        }
        if(count !=0){
            return false;
        }
        return true;
    }
    private boolean checkPossibleMove(int x, int y){
        int X[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int Y[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int newX = 0;
        int newY = 0;
        for(int i=0; i< 8;i++){
            if(currentPlayerNumber == Player1){
                if(player1LastPointX == -1 || player1LastPointY == -1)
                    return true;
                newX = player1LastPointX + X[i];
                newY = player1LastPointY + Y[i];
            }else{
                if(player2LastPointX == -1 || player2LastPointY == -1)
                    return true;
                newX = player2LastPointX + X[i];
                newY = player2LastPointY + Y[i];
            }
            if(newX>=0 && newY>=0 && newX<8 && newY<8
            && (newX == x && newY == y)){
                return true;
            }

        }
        return false;
    }
    public void togglePlayer(){
        if(currentPlayerNumber == Player1){
            currentPlayerNumber = Player2;
        }else{
            currentPlayerNumber = Player1;
        }
    }

    /**
     * Gets Next Turns
     *
     * @return if there is no winner and next player can player
     */
    public boolean getNextTurn() {
        final Figure[][] figures = getBoard().getFiguresArray();
        if (getWinner() != null) {
            return false;
        }
        for (Figure[] figureArray : figures) {
            for (Figure figureValue : figureArray) {
                if (figureValue == null) {

                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Gets Current Player
     *
     * @param firstPlayer Always Starts
     * @return returns the the current player
     */
    public Player getCurrentPlayer(final Player firstPlayer) {
        int firstPlayerNum = getFirstPlayerNum(firstPlayer);
        int[] playersTurns = getPlayersTurns();

        if (playersTurns[Player1] == playersTurns[Player2]) {
            return players[firstPlayerNum];
        } else if (firstPlayerNum == Player1) {
            return players[Player2];

        } else {
            return players[Player1];
        }
    }


    /**
     * Checks players or figures in the columns
     *
     * @param column columns
     * @param playerFigure B or W
     * @return If players figure is within the column or else it will throw an exception
     */
    private int getPlayerForColumn(final int column, final String playerFigure) {
        int playerColumnCount = 0;
        for (int i = 0; i < boardLength; i++) {
            try {
                if ((board.getFigure(i, column) != null) && equalsFigures(i, column, playerFigure)) {
                    playerColumnCount++;
                }
            } catch (InvalidPointException e) {
                e.printStackTrace();
            }
        }
        return playerColumnCount;
    }

    /**
     * Gets Players Turns
     *
     * @return player B if player W just played and viceversa
     */
    private int[] getPlayersTurns() {
        int[] playersTurns = new int[players.length];
        for (int playerNum = 0; playerNum < players.length; playerNum++) {
            playersTurns[playerNum] = getPlayerForBoard(players[playerNum].getFigure().toString());
        }
        return playersTurns;
    }

    private int getPlayerForBoard(final String playerFigure) {
        int playerBoardCount = 0;
        for (int i = 0; i < boardLength; i++) {
            playerBoardCount += getPlayerForLine(i, playerFigure);
        }
        return playerBoardCount;
    }

    /**
     * Checks players or figures in the rows
     *
     * @param row Rows
     * @param playerFigure W or B
     * @return If players figure is within the row or else it will throw an exception
     */
    private int getPlayerForLine(final int row, final String playerFigure) {
        int playerRowCount = 0;
        for (int i = 0; i < board.getRowLength(row); i++) {
            try {
                if ((board.getFigure(row, i) != null) && equalsFigures(row, i, playerFigure)) {
                    playerRowCount++;
                }
            } catch (InvalidPointException e) {
                e.printStackTrace();
            }
        }
        return playerRowCount;
    }

    private int getFirstPlayerNum(final Player firstPlayer) {
        int firstPlayerPositionNum = 0;
        for (int num = 0; num < players.length; num++) {
            if (players[num] == firstPlayer) {
                firstPlayerPositionNum = num;
            }
        }
        return firstPlayerPositionNum;
    }

    /**
     * @param x Coordinate
     * @param y Coordinate
     * @param playerFigure W or B
     * @return
     * @throws InvalidPointException
     */
    private boolean equalsFigures(final int x, final int y, final String playerFigure) throws InvalidPointException {
        return board.getFigure(x, y).toString().equals(playerFigure);
    }
}







