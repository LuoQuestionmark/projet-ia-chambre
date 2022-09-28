// The very basic desire: no jewel, no dirt should be lefted.

package Robot.Desires;

import Robot.Beliefs.Belief;

public class DesireTypeA implements Desire {

    @Override
    public int evaluate(Belief b) {
        if (b.getDirtyCellsIndex().size() > 0) return 0;
        if (b.getJewelCellesIndex().size() > 0) return 0;
        return 1;
    }
    
}
