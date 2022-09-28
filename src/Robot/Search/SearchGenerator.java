package Robot.Search;

import Robot.Robot;
import Robot.Beliefs.Belief;
import Robot.Desires.Desire;

abstract public class SearchGenerator {
    abstract public Search generateSearch(Belief b, Desire d, Robot r);
}
