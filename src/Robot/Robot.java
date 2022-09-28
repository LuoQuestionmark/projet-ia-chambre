package Robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Robot.Actions.Action;
import Robot.Beliefs.Belief;
import Robot.Desires.Desire;
import Robot.Search.Search;
import Robot.Search.SearchGenerator;
import Rooms.Room;
import myUtil.Vec2Int;

public class Robot implements Runnable {
    static final int defaultEnergy = Integer.MAX_VALUE; // a random value

    public boolean alive = true;

    private int energy;
    private Vec2Int coord = new Vec2Int(0, 0);

    private Room room;        // the Room object with 25 pieces.
    private Vision vision;    // the vision class
    private Cleaner cleaner;  // the cleaner class

    private List<Action> intentions = Collections.synchronizedList(new ArrayList<Action>());
    private Belief belief;
    private Desire desire;
    private SearchGenerator searchGenerator;

    public Robot (Room r, Desire d, SearchGenerator sg) {
        this.energy = Robot.defaultEnergy;
        this.room = r;
        this.vision = new Vision(r);
        this.cleaner = new Cleaner(r);
        this.desire = d;
        this.searchGenerator = sg;
    }

    public boolean move (int direction) {
        // 0 -> right
        // 1 -> down
        // 2 -> left
        // 3 -> up
        // return false is the move is not possible

        // illegal moves
        if (direction > 3 || direction < 0) return false;
        if (direction == 0 && this.coord.x == 4) return false;
        if (direction == 1 && this.coord.y == 4) return false;
        if (direction == 2 && this.coord.x == 0) return false;
        if (direction == 3 && this.coord.x == 0) return false;

        // energy limit
        if (this.energy < 1) return false;

        switch (direction) {
            case 0:
                this.coord.x += 1;
                break;
            case 1:
                this.coord.y += 1;
                break;
            case 2:
                this.coord.x -= 1;
                break;
            case 3:
                this.coord.y -= 1;
                break;
            default:
                break;
        }
        this.energy -= 1;
        return true;
    }

    public boolean clean() {
        // clean the piece that the robot is standing on
        // return false if the action is now possible (no energy)

        if (this.energy < 1) {
            return false;
        }

        int r = this.coord.y;
        int c = this.coord.x;

        this.cleaner.cleanCell(r, c);
        return true;
    }

    public boolean pick() {
        // clean the piece that the robot is standing on
        // return false if the action is now possible (no energy)

        if (this.energy < 1) {
            return false;
        }

        int r = this.coord.y;
        int c = this.coord.x;

        this.cleaner.pickJewel(r, c);
        return true;
    }
    
    public boolean doNothing () {
        return true;
    }
    
    synchronized public boolean act() {
        // do something from the intention list
        // or do nothing if there is nothing to do
        if (this.intentions.size() == 0) {
            return false;
        }
        // pop the action
        Action a = this.intentions.get(0);
        a.doThis();
        this.intentions.remove(0);
        return true;
    }

    synchronized public Belief observe() {
        // yes this function update the belief in two ways
        // just like the time() from <time.h> in C.
        this.belief = new Belief(this.coord, this.vision.getDirtyCellsIndex(), this.vision.getJewelCellsIndex());
        return this.belief;
    }
    
    @Override
    synchronized public String toString() {
        String out = new String();
        out += "robot status: ";
        out += String.format("located at coord %d, %d, ", this.coord.x, this.coord.y);
        out += String.format("with energy %d\n", this.energy);
        out += "current intentions: \n";
        synchronized(this.intentions) {
            for (Action a: this.intentions) {
                out += "|-" + a.toString() + "\n";
            }
        }
        return out;
    }

    public void run() {
        // Desire desire = new DesireTypeA();
        Search search = this.searchGenerator.generateSearch(observe(), desire, this);
        while (alive) {
            observe();
            this.room.setRobotCoord(this.coord);
            synchronized(this.intentions) {
                if (this.intentions.size() == 0) {
                    this.intentions.addAll((search.doSearch(this.belief)));
                }
            }
            this.act();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // don't wake me up...
                throw new UnknownError("Who wake this program up?");
            }

            // System.out.println(this);
        }
    }
}
