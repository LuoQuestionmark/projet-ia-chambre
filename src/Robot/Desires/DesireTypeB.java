// Another basic desire: no jewel, no dirt should be lefted.
// and no jewel should be vacuumed

package Robot.Desires;

import Robot.Beliefs.Belief;

public class DesireTypeB implements Desire {

    @Override
    public int evaluate(Belief b) {
        if (b.dirtyCellsIndex.size() > 0) return 0;
        if (b.jewelCellsIndex.size() > 0) return 0;
        if (b.jewelCleaned > 0) return 0;
        return 1;
    }
    
}
