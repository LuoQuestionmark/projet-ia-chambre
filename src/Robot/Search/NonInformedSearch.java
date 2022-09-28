package Robot.Search;

import java.util.ArrayDeque;
import java.util.ArrayList;

import Robot.Robot;
import Robot.Actions.*;
import Robot.Beliefs.*;
import Robot.Desires.*;
import myUtil.Vec2Int;

public class NonInformedSearch implements Search {
    private Belief belief;
    private Desire desire;
    private Robot robot;

    public NonInformedSearch(Belief b, Desire d, Robot r) {
        this.belief = b;
        this.desire = d;
        this.robot = r;
    }

    private ArrayList<Action> legalActions(Belief situation) {
        // list every possible action from current situation
        ArrayList<Action> out = new ArrayList<>();

        // if no energy left, then boom
        if (situation.energyAvailable == 0) return out;

        // otherwise let us start:
        // possible moves:
        if (situation.coord.x > 0) {
            out.add(new Move(this.robot, 2));
        }
        if (situation.coord.x < 4) {
            out.add(new Move(this.robot, 0));
        }
        if (situation.coord.y > 0) {
            out.add(new Move(this.robot, 3));
        }
        if (situation.coord.y < 4) {
            out.add(new Move(this.robot, 1));
        }
        // possible action:
        // the check can reduce some possibilities
        for (Vec2Int v: this.belief.jewelCellsIndex) {
            if (v.equals(situation.coord)) {
                out.add(new Pick(this.robot));
            }
        }

        for (Vec2Int v: this.belief.dirtyCellsIndex) {
            if (v.equals(situation.coord)) {
                out.add(new Vacuum(this.robot));
            }
        }

        out.add(new NoAction());

        return out;
    }

    public ArrayList<Action> doSearch(Belief b) {
        ArrayDeque<ArrayList<Action>> BFSListActions = new ArrayDeque<>();
        ArrayDeque<Belief> BFSListBeliefs = new ArrayDeque<>();

        BFSListActions.add(new ArrayList<>());
        BFSListBeliefs.add(b);

        while (BFSListBeliefs.size() > 0) {
            Belief belief = BFSListBeliefs.pollFirst();
            ArrayList<Action> action = BFSListActions.pollFirst();
            for (Action a: legalActions(belief)) {
                Belief newBelief = belief.act(a);
                ArrayList<Action> newAction = (ArrayList<Action>)action.clone();
                newAction.add(a);
                if (desire.evaluate(newBelief) > 0) {
                    return newAction;
                }
                BFSListActions.add(newAction);
                BFSListBeliefs.add(newBelief);
            }
            
        }

        // no legal action, this shouldn't be possible...
        // anyway return null means failure
        return null;
    }
}
