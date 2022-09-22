package Rooms;
// a cell is a part of a room, containing a jewel and/or a dirt or nothing.
// a jewel or a dirt can be cleaned or be generated.
public class Cell {
    private boolean jewelState = false;
    private boolean dirtState = false;
    
    public boolean hasJewel() {
        return this.jewelState;
    }
    public boolean hasDirt() {
        return this.dirtState;
    }

    public void setJewel(boolean state) {
        this.jewelState = state;
    }
    public void setDirt(boolean state) {
        this.dirtState = state;
    }
}
