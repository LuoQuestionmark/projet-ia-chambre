package Robot.Search;

import java.util.ArrayList;

import Robot.Actions.Action;
import Robot.Beliefs.Belief;

public interface Search {
    public ArrayList<Action> doSearch(Belief b);
}
