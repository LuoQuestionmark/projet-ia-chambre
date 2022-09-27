package Robot.Actions;

import Robot.Robot;

public class Move implements Action{

    private Robot robot;
    private int direction;

    public Move(Robot r, int direction) {
        this.robot = r;
        this.direction = direction;
    }

    @Override
    public void doThis() {
        robot.move(direction);
    }
}
