package gameinterface;

import gameinterface.MyLocation;

/**
 * MineSweeper model interface, to make it easier to attach whatever kind of UI or test class to the game
 */
public interface MineSweeperModel {
    /**
     * Returns the width of the game board
     * @return width of board
     */
    int getWidth();

    /**
     * Returns the height of the game board
     * @return height of board
     */
    int getHeight();

    /**
     * Returns the value of a cell on the game board
     * @param location the location of the cell with row and column
     * @return value of cell on specified row and column
     */
    String getValueAt(gameinterface.Location location) throws InvalidRangeException;

    /**
     * Method to fire a checkLocation on a cell on the game board
     * @param location the location of the cell with row and column
     */
    void checkLocation(gameinterface.Location location) throws InvalidRangeException;

    /**
     * Method to fire a flagLocation on a cell on the game board
     * @param location the location of the cell with row and column
     */
    void flagLocation(gameinterface.Location location) throws InvalidRangeException;
    /**
     * The number of actions so far
     * @return the number of clicks
     */
    int getNrOfActions();

    /**
     * The number of mines left uncovered by a flag
     * @return the number of mines to discover
     */
    long getNrOfMinesLeft();

    /**
     * Method to indicate the game is lost (by clicking a mine)
     * @return true if the game is lost
     */
    boolean getLost();

    /**
     * Method to getValue a game board
     */
    default void printBoard() throws InvalidRangeException {
        for (int i = 0; i < getHeight(); i++) {
            System.out.print((i+1)%10);
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(" " + getValueAt(new MyLocation(j,i)) + " ");
            }
            System.out.println();
        }
        System.out.print(" ");
        for (int k = 0; k < getWidth(); k++) {
            int t = k + 1;
            System.out.print(" " + t%10  + " ");
        }
        System.out.println(" ");

        System.out.println("Number of Actions: " + getNrOfActions() + " :-: Mines to Go: " + getNrOfMinesLeft());
    }
}
