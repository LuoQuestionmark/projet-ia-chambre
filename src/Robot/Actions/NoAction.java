package Robot.Actions;

public class NoAction implements Action{

    @Override
    public void doThis() {
        return;
    }
    
    @Override
    public String toString() {
        return "no action";
    }
}
