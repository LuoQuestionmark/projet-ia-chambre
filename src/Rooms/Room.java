// A room is a collection of 25 cells. (This number is defined as cellNum in the Room class)

package Rooms;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom; // using this instead of Random because of the 
                                               // possible implementation of multithread in 
                                               // the future.

public class Room {
    private final int cellNum = 25;
    private ArrayList<Cell> cells;

    public Room() {
        this.cells = new ArrayList<>();
        for (int i=0; i < cellNum; i++) {
            this.cells.add(new Cell());
        }
    }

    public ArrayList<Cell> getCleanCells () {
        ArrayList<Cell> out = new ArrayList<>();
        for (Cell c: cells) {
            if (!(c.hasDirt())) {
                out.add(c);
            }
        }
        return out;
    }

    public ArrayList<Cell> getNoJewelCells () {
        ArrayList<Cell> out = new ArrayList<Cell>();
        for (Cell c: cells) {
            if (!(c.hasJewel())) {
                out.add(c);
            }
        }
        return out;
    }

    public boolean geneJewel() {
        ArrayList<Cell> cleanCells = getNoJewelCells();
        if (cleanCells.size() == 0) return false;

        int roomIndex = ThreadLocalRandom.current().nextInt(cleanCells.size());
        this.cells.get(roomIndex).setJewel(true);

        return true;
    }

    public boolean geneJewel(int n) {
        while (n > 0) {
            if (geneJewel() == false) return false;
            else n -= 1;
        }
        return true;
    }

    public boolean geneDirt(int n) {
        while (n > 0) {
            if (geneDirt() == false) return false;
            else n -= 1;
        }
        return true;
    }

    public boolean geneDirt() {
        ArrayList<Cell> cleanCells = getCleanCells();
        if (cleanCells.size() == 0) return false;

        int roomIndex = ThreadLocalRandom.current().nextInt(cleanCells.size());
        this.cells.get(roomIndex).setDirt(true);

        return true;
    }

    public void printRoom() {
        // Attention: this function only work when there is exactly 25 pieces.
        // this function provide an easy way to visualize the status of the room.
        // @ : both jewel and dirt
        // ^ : dirt
        // * : jewel
        // (empty) : empty
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                int index = i * 5 + j;
                Cell c = this.cells.get(index);
                if (c.hasDirt() && c.hasJewel()) {
                    System.out.print(" @ |");
                }
                else if (c.hasDirt()) {
                    System.out.print(" ^ |");
                }
                else if (c.hasJewel()) {
                    System.out.print(" * |");
                }
                else {
                    System.out.print("   |");
                }
            }
            System.out.print("\n");
        }
    }
}
