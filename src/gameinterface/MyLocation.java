package gameinterface;

public class MyLocation implements Location {

    private int row;
    private int column;

    public MyLocation(int column, int row) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    protected String getValue() {
        return null;
    }

    protected void setFlag(Boolean bool){
    }

    protected Boolean isFlag(){
        return false;
    }

    protected TileType getType(){
        return null;
    }

    protected void setType(TileType type){
    }

    protected int getNeighbours(){
        return 0;
    }

    protected void setNeighbours(int i){
    }

    protected boolean getDiscovered(){return false;}

    protected void setDiscovered(Boolean bool){}

    @Override
    public String toString() {
        return "MyLocation{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
