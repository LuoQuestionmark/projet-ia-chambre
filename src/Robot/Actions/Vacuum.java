package Robot.Actions;

import Robot.Robot;

public class Vacuum implements Action{

    private Robot robot;

    public Vacuum(Robot r) {
        this.robot = r;
    }

    @Override
    public void doThis() {
        robot.clean();
    }

    @Override
    public String toString() {
        return "action: vacuum";
    }
}
