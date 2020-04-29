package gameinterface;

public enum TileType {
    EMPTY ("."), NUMBER ("N"), BOMB ("X");


    private String symbol;

    TileType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}


