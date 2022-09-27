package Robot.Beliefs;

import java.util.ArrayList;

import myUtil.Vec2Int;

public class Belief {
    public Vec2Int coord;
    public ArrayList<Vec2Int> dirtyCellsIndex;
    public ArrayList<Vec2Int> JewelcellsIndex;

    public int dirtCleaned = 0;
    public int jewelCleaned = 0;
    public int jewelCollected = 0;
    public int energyUsed = 0;

    public Belief(Vec2Int coord, ArrayList<Vec2Int> dirtyCellsIndex, ArrayList<Vec2Int> JewelcellsIndex) {
        this.coord = coord;
        this.dirtyCellsIndex = dirtyCellsIndex;
        this.JewelcellsIndex = JewelcellsIndex;
    }
}
