import Rooms.*;
import Robot.*;
import Robot.Desires.*;
import Robot.Search.InformedSearchGenerator;
import Robot.Search.SearchGenerator;

public class Test3 {
    public static void main(String[] args) throws Exception {
        Room r = new Room();
        SearchGenerator sg = new InformedSearchGenerator();
        Robot bot = new Robot(r, new DesireTypeC(), sg);

        Thread roomThread = new Thread(r);
        Thread botThread = new Thread(bot);
        roomThread.start();
        botThread.start();
        r.setAutoGenerate(true);
        while (true) {
            r.printRoom();
            // System.out.print(bot);
            Thread.sleep(1000);
        }
    }
}
