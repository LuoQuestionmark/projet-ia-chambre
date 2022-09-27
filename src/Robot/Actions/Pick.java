package Robot.Actions;

import Robot.Robot;

public class Pick implements Action{

    private Robot robot;

    public Pick(Robot r) {
        this.robot = r;
    }

    @Override
    public void doThis() {
        robot.pick();
    }
}
