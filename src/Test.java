import Rooms.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Room r = new Room();
        System.out.println("start with an empty room: ");
        r.printRoom();

        System.out.println("Add some dirts");
        r.geneDirt(3);
        r.printRoom();


        System.out.println("Add some jewel");
        r.geneJewel(3);
        r.printRoom();
    }
}
