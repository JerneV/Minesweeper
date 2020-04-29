package gameinterface;

public class Tile extends MyLocation{

    private Boolean discovered = false;
    private MyMinesweeper board;
    private TileType type;
    private int neighbours = 0;
    private boolean isFlag;

    public Tile(int column, int row, MyMinesweeper board) {
        super(column, row);
        this.board = board;
        this.type = TileType.EMPTY;
    }

    @Override
    protected boolean getDiscovered() {
        return discovered;
    }

    @Override
    public void setDiscovered(Boolean discovered) {
        this.discovered = discovered;
    }

    @Override
    protected String getValue() {
        if(isFlag) {
            return "F";
        }
        if(discovered){
            if(type.getSymbol().equals("N")){
                return String.valueOf(neighbours);
            }else if(type.getSymbol().equals(".")){
                return " ";
            }
            return type.getSymbol();
        }
        return ".";
    }

    @Override
    protected void setFlag(Boolean bool) {
        isFlag = bool;
    }

    @Override
    protected Boolean isFlag() {
        return isFlag;
    }

    @Override
    protected int getNeighbours() {
        return neighbours;
    }

    @Override
    protected void setNeighbours(int i) {
        neighbours = i;
    }

    @Override
    protected TileType getType() {
        return type;
    }

    @Override
    protected void setType(TileType type) {
        this.type = type;
    }

}
