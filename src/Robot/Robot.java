package Robot;

import Rooms.Room;
import myUtil.Vec2Int;

public class Robot {
    static final int defaultEnergy = 10; // a random value

    private int energy;
    private Vec2Int coord = new Vec2Int(0, 0);

    private Room room;        // the Room object with 25 pieces.
    private Vision vision;    // the vision class
    private Cleaner cleaner;  // the cleaner class

    public Robot (Room r) {
        this.energy = Robot.defaultEnergy;
        this.room = r;
        this.vision = new Vision(r);
        this.cleaner = new Cleaner(r);
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

        int r = this.coord.x;
        int c = this.coord.y;

        this.cleaner.cleanCell(r, c);
        return true;
    }

    public boolean pick() {
        // clean the piece that the robot is standing on
        // return false if the action is now possible (no energy)

        if (this.energy < 1) {
            return false;
        }

        int r = this.coord.x;
        int c = this.coord.y;

        this.cleaner.pickJewel(r, c);
        return true;
    }
    
}
