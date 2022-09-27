package Robot.Beliefs;

import java.util.ArrayList;

import myUtil.Vec2Int;

public class Belief {
    public Vec2Int coord;
    public ArrayList<Vec2Int> dirtyCellsIndex;
    public ArrayList<Vec2Int> JewelcellsIndex;

    public Belief(Vec2Int coord, ArrayList<Vec2Int> dirtyCellsIndex, ArrayList<Vec2Int> JewelcellsIndex) {
        this.coord = coord;
        this.dirtyCellsIndex = dirtyCellsIndex;
        this.JewelcellsIndex = JewelcellsIndex;
    }
}
