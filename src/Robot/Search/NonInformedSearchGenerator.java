package Robot.Search;

import Robot.Robot;
import Robot.Beliefs.Belief;
import Robot.Desires.Desire;

public class NonInformedSearchGenerator extends SearchGenerator {
    public Search generateSearch(Belief b, Desire d, Robot r) {
        return new NonInformedSearch(b, d, r);
    }
}
