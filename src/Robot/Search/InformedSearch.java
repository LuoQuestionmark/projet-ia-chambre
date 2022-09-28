package Robot.Search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import Robot.Robot;
import Robot.Actions.*;
import Robot.Beliefs.Belief;
import Robot.Desires.Desire;
import Robot.Desires.DesireTypeC;
import myUtil.Vec2Int;

public class InformedSearch implements Search {
    private Belief belief;
    private Desire desire = new DesireTypeC();
    private Robot robot;
    
    public InformedSearch(Belief b, Robot r) {
        this.belief = b;
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

        return out;
    }

    @Override
    public ArrayList<Action> doSearch(Belief b) {
        // this time it's a min heap,
        // so that we basically implement a greedy algorithm
        // using the function from DesireTypeC as the
        // evaluation function.

        if (b.dirtyCellsIndex.size() == 0 &&
            b.jewelCellsIndex.size() == 0) {
            return new ArrayList<Action>();
        }


        PriorityQueue<Belief> queue = new PriorityQueue<>(new InnerComparator(this.desire));

        HashMap<Belief, ArrayList<Action>> dict = new HashMap<Belief,ArrayList<Action>>();
        queue.add(b);
        dict.put(b, new ArrayList<>());

        while (queue.size() > 0) {
            Belief situation = queue.poll();
            for (Action a: legalActions(situation)) {
                ArrayList<Action> actions = new ArrayList<>(dict.get(situation));
                Belief newBelief = situation.act(a);
                actions.add(a);

                // int score = this.desire.evaluate(newBelief);

                // exit condition
                if (newBelief.dirtyCellsIndex.size() == 0 &&
                    newBelief.jewelCellsIndex.size() == 0) {
                        // exit
                        return actions;
                    }

                queue.add(newBelief);
                dict.put(newBelief, actions);
            }
            dict.remove(situation);
        }

        // I believe the program shouldn't go here
        return null;
    }
    
}

/**
 * InnerInformedSearch
 */
class InnerComparator implements Comparator<Belief> {
    private Desire desire;

    public InnerComparator(Desire desire) {
        this.desire = desire;
    }

    @Override
    public int compare(Belief o1, Belief o2) {
        // a better situation make more score,
        // thus by doing this we have o1 < o2 if o1 is better
        int out = desire.evaluate(o2) - desire.evaluate(o1);
        return out;
    }

}
