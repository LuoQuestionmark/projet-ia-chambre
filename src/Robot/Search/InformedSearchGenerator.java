package Robot.Search;

import Robot.Robot;
import Robot.Beliefs.Belief;
import Robot.Desires.Desire;

public class InformedSearchGenerator extends SearchGenerator {
    public Search generateSearch(Belief b, Robot r) {
        return new InformedSearch(b, r);
    }
    public Search generateSearch(Belief b, Desire d, Robot r) {
        return new InformedSearch(b, r);
    }
}
