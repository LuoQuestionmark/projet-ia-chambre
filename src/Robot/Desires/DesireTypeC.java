package Robot.Desires;

import java.lang.Math;

import Robot.Beliefs.Belief;
import myUtil.Vec2Int;

public class DesireTypeC implements Desire {

    @Override
    public int evaluate(Belief b) {
        // higher score is better
        // so start with baseScore, then
        // for other score we add
        // to the bonusScore
        final int baseScore = 1000;
        int bonusScore = 0;

        bonusScore += 2 * b.dirtCleaned;
        bonusScore += 3 * b.jewelCollected;
        bonusScore -= 5 * b.jewelCleaned;
        bonusScore -= 1 * b.energyUsed;
        
        for (Vec2Int v: b.dirtyCellsIndex) {
            bonusScore -= Math.abs(v.x - b.coord.x);
            bonusScore -= Math.abs(v.y - b.coord.y);
        }

        for (Vec2Int v: b.jewelCellsIndex) {
            bonusScore -= Math.abs(v.x - b.coord.x);
            bonusScore -= Math.abs(v.y - b.coord.y);
        }

        return baseScore + bonusScore;
    }
    
}
