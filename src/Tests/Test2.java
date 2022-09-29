package Tests;
import Rooms.*;
import Robot.*;
import Robot.Desires.*;
import Robot.Search.NonInformedSearchGenerator;
import Robot.Search.SearchGenerator;

public class Test2 {
    public static void main(String[] args) throws Exception {
        Room r = new Room();
        SearchGenerator sg = new NonInformedSearchGenerator();
        Robot bot = new Robot(r, new DesireTypeB(), sg);

        Thread roomThread = new Thread(r);
        Thread botThread = new Thread(bot);
        roomThread.start();
        botThread.start();
        r.geneDirt(3);
        while (true) {
            r.printRoom();
            System.out.print(bot);
            Thread.sleep(1000);
        }
    }
}
