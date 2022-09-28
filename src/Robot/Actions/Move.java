package Robot.Actions;

import Robot.Robot;

public class Move implements Action{

    private Robot robot;
    private int direction;

    public Move(Robot r, int direction) throws IllegalArgumentException {
        if (direction < 0 || direction > 3) {
            throw new IllegalArgumentException("invalid direction");
        }
        this.robot = r;
        this.direction = direction;
    }

    @Override
    public void doThis() {
        robot.move(direction);
    }

    @Override
    public String toString() {
        String dir = new String();
        switch (direction) {
            case 0:
                dir = "right";
                break;
            case 1:
                dir = "down";
                break;
            case 2:
                dir = "left";
                break;
            case 3:
                dir = "up";
                break;
        
            default:
                // lol, the program break
                dir = "emm...";
                break;
        }
        return "action: move" + dir;
    }

    public int getDirection() {
        return this.direction;
    }
}
