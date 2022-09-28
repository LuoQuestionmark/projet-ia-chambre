package Robot.Beliefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Robot.Actions.Action;
import Robot.Actions.Move;
import Robot.Actions.Pick;
import Robot.Actions.Vacuum;
import myUtil.Vec2Int;

public class Belief {
    public Vec2Int coord;
    volatile public List<Vec2Int> dirtyCellsIndex;
    volatile public List<Vec2Int> jewelCellsIndex;

    public List<Vec2Int> getDirtyCellsIndex() {
        synchronized (dirtyCellsIndex) {
            return dirtyCellsIndex;
        }
    }

    public List<Vec2Int> getJewelCellesIndex() {
        synchronized (jewelCellsIndex) {
            return jewelCellsIndex;
        }
    }

    public int dirtCleaned = 0;
    public int jewelCleaned = 0;
    public int jewelCollected = 0;
    public int energyUsed = 0;
    public int energyAvailable = Integer.MAX_VALUE; // after all it's free

    public Belief(Vec2Int coord, List<Vec2Int> dirtyCellsIndex, List<Vec2Int> JewelcellsIndex) {
        this.coord = coord;
        this.dirtyCellsIndex = Collections.synchronizedList(dirtyCellsIndex);
        this.jewelCellsIndex = Collections.synchronizedList(JewelcellsIndex);
    }

    synchronized public Belief act(Action a) throws IllegalArgumentException {
        // create a new belief from an old one with an action
        // first let's copy everything
        // Belief out = new Belief(new Vec2Int(this.coord.x, this.coord.y), (ArrayList<Vec2Int>)this.getDirtyCellsIndex().clone(), (ArrayList<Vec2Int>)this.getJewelCellesIndex().clone());

        List<Vec2Int> tmpDirtyCells = new ArrayList<>();
        synchronized(this.dirtyCellsIndex){
            for (Vec2Int v: this.getDirtyCellsIndex()) {
                tmpDirtyCells.add(new Vec2Int(v.x, v.y));
            }
        }
        List<Vec2Int> newDirtyCells = Collections.synchronizedList(tmpDirtyCells);

        List<Vec2Int> tmpJewelCells = new ArrayList<>();
        synchronized(this.jewelCellsIndex){
            for (Vec2Int v: this.getJewelCellesIndex()) {
                tmpJewelCells.add(v);
            }
        }
        List<Vec2Int> newJewelCells = Collections.synchronizedList(tmpJewelCells);

        Belief out = new Belief(new Vec2Int(this.coord.x, this.coord.y), newDirtyCells, newJewelCells);

        if (a instanceof Move) {
            Move m = (Move)a;
            switch (m.getDirection()) {
                case 0:
                    if (out.coord.x < 4) {
                        out.coord.x += 1;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case 1:
                    if (out.coord.y < 4) {
                        out.coord.y += 1;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case 2:
                    if (out.coord.x > 0) {
                        out.coord.x -= 1;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case 3:
                    if (out.coord.y > 0) {
                        out.coord.y -= 1;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        else if (a instanceof Pick) {
            ArrayList<Vec2Int> tmp = new ArrayList<>();
            for (Vec2Int coord: out.getJewelCellesIndex()) {
                if (out.coord.equals(coord)) {
                    tmp.add(coord);
                    out.jewelCollected += 1;
                }
            }
            out.jewelCellsIndex.removeAll(tmp);
        }
        else if (a instanceof Vacuum) {
            synchronized(dirtyCellsIndex) {
                ArrayList<Vec2Int> tmp = new ArrayList<>();
                for (Vec2Int coord: out.getDirtyCellsIndex()) {
                    if (out.coord.equals(coord)) {
                        tmp.add(coord);
                        //out.dirtyCellsIndex.remove(coord);
                        out.dirtCleaned += 1;
                    }
                }
                out.getDirtyCellsIndex().removeAll(tmp);
            }
            synchronized(jewelCellsIndex) {
                ArrayList<Vec2Int> tmp = new ArrayList<>();
                for (Vec2Int coord: out.getJewelCellesIndex()) {
                    if (out.coord.equals(coord)) {
                        tmp.add(coord);
                        // out.jewelCellsIndex.remove(coord);
                        out.jewelCleaned += 1;
                    } 
                }
                out.getJewelCellesIndex().removeAll(tmp);
            }
        }
        else {
            throw new IllegalArgumentException("illegal action: " + a.toString());
        }
        return out;
    }
}
