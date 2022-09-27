package Robot.Desires;

import Robot.Beliefs.Belief;

public interface Desire {
    // desire is evaluate by the belief of current state, logic. 
    // lets say that a higher score is better, and anything <= 0
    // is "unacceptable".
    public int evaluate(Belief b);
}
