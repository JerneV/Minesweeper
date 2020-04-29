package gameinterface;

import java.util.Random;


public class MyMinesweeper implements MineSweeperModel {

    Random random = new Random();
    private int rows;
    private int columns;
    private int bombs;
    private boolean getLost;
    private int nrOfActions = 0;
    private int defusedBombs = 0;
    private int correctBombs = 0;
    private MyLocation[][] board;

    /**
     * One of the two main methods used for making a new minesweeper game.
     * @param rows the INT amount of rows the board has
     * @param columns the INT amount of columns the board has
     * @param bombs the INT number of bombs the game has
     * @throws InvalidRangeException thrown when conditions are not met
     */
    public MyMinesweeper(int rows, int columns, int bombs) throws InvalidRangeException {
        if (rows * columns < bombs) {
            throw new InvalidRangeException("You can't have more bombs than tiles!");
        }
        if (rows <= 0 || columns <= 0 || bombs <= 0) {
            throw new InvalidRangeException("Parameters need to be positive!");
        }

        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
        //Generate an empty board with width = columns and height = rows
        this.board = new MyLocation[columns][rows];
        fillFieldEmpty();
    }

    /**
     * Basically the same method as above but the amount of bombs are randomized
     */
    public MyMinesweeper(int rows, int columns) throws InvalidRangeException {
        if (rows <= 0 || columns <= 0) {
            throw new InvalidRangeException("Parameters need to be positive!");
        }

        this.rows = rows;
        this.columns = columns;
        int max = (rows * columns) / 5;
        int min = (rows * columns) / 5 - (rows + columns);
        this.bombs = random.nextInt((max - min) + 1) + min;

        this.board = new MyLocation[columns][rows];
        fillFieldEmpty();

    }

    /**
     * Fills the board with empty tiles
     */
    public void fillFieldEmpty() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                //Fill the board with empty tiles!
                board[x][y] = new Tile(x, y, this);
            }
        }
    }

    @Override
    public int getWidth() {
        return columns;
    }

    @Override
    public int getHeight() {
        return rows;
    }

    @Override
    public String getValueAt(Location location) {
        if (isValidLocation(location)) {
            return board[location.getColumn()][location.getRow()].getValue();
        }
        return null;
    }

    /**
     * Will return a boolean when the entered MyLocation is a valid one within the current board
     * @param location MyLocation we want to check
     * @return a boolean whether the location is valid
     */
    public Boolean isValidLocation(Location location) {
        int x = location.getColumn();
        int y = location.getRow();
        return x >= 0 && y <= rows - 1 && y >= 0 && x <= columns - 1;
    }

    /**
     * Checks a given location. First time generates the bombs
     * @param location the location of the cell with row and column
     */
    @Override
    public void checkLocation(Location location) {
        if (isValidLocation(location)) {
            if (nrOfActions == 0) {
                generateBombs(location);
                checkAllTiles();
                reveal(location);
                nrOfActions += 1;
            } else if(!board[location.getColumn()][location.getRow()].isFlag()){
                nrOfActions += 1;
                if (board[location.getColumn()][location.getRow()].getType() == TileType.BOMB) {
                    getLost = true;
                } else {
                    reveal(location);
                }

            }
        }
    }

    /**
     * Checks all tiles and their neighbours, I added this to fix a non-related issue but I do like it better this way
     */
    private void checkAllTiles() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Location nLoc = new MyLocation(x, y);
                // should always be a valid position!
                int nei = getNeighbours(nLoc);
                if (board[x][y].getType() == TileType.EMPTY) {
                    if (nei > 0) {
                        board[x][y].setType(TileType.NUMBER);
                        board[x][y].setNeighbours(nei);
                    }
                }
            }
        }
    }

   /* private void reveal(Location location) {
        //needs to reveal the clicked tile at location
        // reveal is never a bomb!
        // but needs to count surrounding bombs
        // then it calls reveal on all neighbouring tiles
        // stops when encounters a number tile or a bomb tile!
        // or when a tile is already revealed!
        int tileX = location.getColumn();
        int tileY = location.getRow();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Location revealLoc = new MyLocation(tileX + x, tileY + y);
                if (isValidLocation(revealLoc)) {
                    if (board[revealLoc.getColumn()][revealLoc.getRow()].getType() == TileType.EMPTY) {
                        if (getNeighbours(revealLoc) > 0) {
                            board[revealLoc.getColumn()][revealLoc.getRow()].setType(TileType.NUMBER);
                            board[revealLoc.getColumn()][revealLoc.getRow()].setDiscovered(true);
                            return;
                        } else {
                            board[revealLoc.getColumn()][revealLoc.getRow()].setDiscovered(true);
                        }
                    } else if (board[revealLoc.getColumn()][revealLoc.getRow()].getType() == TileType.BOMB) {
                        return;
                    }
                }
            }
        }

    }*/

   /* private void reveal2(Location location) {
        int tileX = location.getColumn();
        int tileY = location.getRow();
        if (!board[tileX][tileY].getDiscovered()) {
            board[tileX][tileY].setDiscovered(true);
            if (board[location.getColumn()][location.getRow()].getType() == TileType.EMPTY) {
                if (getNeighbours(location) > 0) {
                    board[location.getColumn()][location.getRow()].setType(TileType.NUMBER);
                } else {
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            Location revealLoc = new MyLocation(tileX + x, tileY + y);
                            if (isValidLocation(revealLoc)) {
                                reveal2(revealLoc);
                            }

                        }
                    }
                }
            }
        }
    }*/

    /**
     * Reveals alll revealable tiles and their neighbours
     * @param location the starting location for the reveal
     */
    private void reveal(Location location) {
        int tileX = location.getColumn();
        int tileY = location.getRow();
        if (!board[tileX][tileY].getDiscovered()) {
            board[tileX][tileY].setDiscovered(true);
            if (board[location.getColumn()][location.getRow()].getType() != TileType.NUMBER && !board[location.getColumn()][location.getRow()].isFlag()) {
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        Location revealLoc = new MyLocation(tileX + x, tileY + y);
                        if (isValidLocation(revealLoc)) {
                            reveal(revealLoc);
                        }
                    }

                }
            }
        }
    }

    /**
     * Generates all bombs but not in the 3x3 surrounding the first tile!
     * @param location of the first tile
     */
    private void generateBombs(Location location) {
        int placedBombs = 0;
        while (placedBombs < bombs) {
            Location bombLoc = new MyLocation(random.nextInt(getWidth()), random.nextInt(getHeight()));
            if (isValidLocation(bombLoc)) {
                if(!((location.getColumn()-1 <= bombLoc.getColumn() && bombLoc.getColumn() <= location.getColumn()+1) || (location.getRow()-1 <= bombLoc.getRow() && bombLoc.getRow() <= location.getRow()+1))){
                    if (board[bombLoc.getColumn()][bombLoc.getRow()].getType() == TileType.EMPTY) {
                        board[bombLoc.getColumn()][bombLoc.getRow()].setType(TileType.BOMB);
                        placedBombs += 1;
                    }
                }
            }
        }
    }

    /**
     * This functions returns the amount of neighbours a certain tile has
     * @param location of the tile we need to check
     * @return INT amount of neighbours
     */
    public int getNeighbours(Location location) {
        int tileX = location.getColumn();
        int tileY = location.getRow();
        int neighbours = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Location checkLoc = new MyLocation(tileX + x, tileY + y);
                if (isValidLocation(checkLoc)) {
                    if (board[checkLoc.getColumn()][checkLoc.getRow()].getType() == TileType.BOMB) {
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }


    /**
     * Flags a location, keeps track of correctly flagged tiles
     * @param location the location of the cell with row and column
     * @throws InvalidRangeException not in range of field
     */
    @Override
    public void flagLocation(Location location) throws InvalidRangeException {
        if(isValidLocation(location)) {
            int tileX = location.getColumn();
            int tileY = location.getRow();
            if (board[tileX][tileY].isFlag()) {
                board[tileX][tileY].setFlag(false);
                defusedBombs--;
                if(board[location.getColumn()][location.getRow()].getType() == TileType.BOMB){
                    correctBombs--;
                }
            } else if (!board[tileX][tileY].getDiscovered()) {
                if(board[location.getColumn()][location.getRow()].getType() == TileType.BOMB){
                    correctBombs++;
                }
                board[tileX][tileY].setFlag(true);
                defusedBombs++;
                nrOfActions++;
            }
        }else{
            throw new InvalidRangeException("Not in range of the playfield!");
        }
    }

    @Override
    public int getNrOfActions() {
        return nrOfActions;
    }

    @Override
    public long getNrOfMinesLeft() {
        return bombs - defusedBombs;
    }

    public int remainingBombs(){
        return bombs - correctBombs;
    }

    @Override
    public boolean getLost() {
        return getLost;
    }

}
