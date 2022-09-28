// A room is a collection of 25 cells. (This number is defined as cellNum in the Room class)

package Rooms;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom; // using this instead of Random because of the 
                                               // possible implementation of multithread in 
                                               // the future.

import myUtil.Vec2Int;

public class Room implements Runnable {
    private final int cellNum = 25;
    private ArrayList<Cell> cells;

    private Vec2Int robotCoord = null;

    private boolean addJewel = false;
    private boolean addDirt = false;

    volatile public boolean isRunning = false;
    volatile public boolean autoGenerate = false;

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    private double dirtGeneratePerSecond = 0.5;
    private double jewelGeneratePerSecond = 0.4;
    long lastUpdate = System.currentTimeMillis();

    public Room() {
        this.cells = new ArrayList<>();
        for (int i=0; i < cellNum; i++) {
            this.cells.add(new Cell());
        }
    }

    synchronized public ArrayList<Cell> getCleanCells () {
        ArrayList<Cell> out = new ArrayList<>();
        for (Cell c: cells) {
            if (!(c.hasDirt())) {
                out.add(c);
            }
        }
        return out;
    }

    synchronized public ArrayList<Integer> getCleanCellsIndex () {
        ArrayList<Integer> out = new ArrayList<>();
        for (Cell c: cells) {
            if (!(c.hasDirt())) {
                out.add(cells.indexOf(c));
            }
        }
        return out;
    }

    synchronized public ArrayList<Cell> getNoJewelCells () {
        ArrayList<Cell> out = new ArrayList<Cell>();
        for (Cell c: cells) {
            if (!(c.hasJewel())) {
                out.add(c);
            }
        }
        return out;
    }

    synchronized public ArrayList<Integer> getNoJewelCellsIndex () {
        ArrayList<Integer> out = new ArrayList<>();
        for (Cell c: cells) {
            if (!(c.hasJewel())) {
                out.add(cells.indexOf(c));
            }
        }
        return out;
    }

    synchronized public boolean geneJewel() {
        // generate a jewel instantly, low-level
        ArrayList<Cell> cleanCells = getNoJewelCells();
        if (cleanCells.size() == 0) return false;

        int roomIndex = ThreadLocalRandom.current().nextInt(cleanCells.size());
        cleanCells.get(roomIndex).setJewel(true);

        return true;
    }

    synchronized public boolean geneJewel(int n) {
        // generate <n> jewel instantly, low-level
        while (n > 0) {
            if (geneJewel() == false) return false;
            else n -= 1;
        }
        return true;
    }
    
    synchronized public boolean geneDirt() {
        // generate a dirt instantly, low-level
        ArrayList<Cell> cleanCells = getCleanCells();
        if (cleanCells.size() == 0) return false;
        
        int roomIndex = ThreadLocalRandom.current().nextInt(cleanCells.size());
        cleanCells.get(roomIndex).setDirt(true);
        
        return true;
    }
    
    synchronized public boolean geneDirt(int n) {
        // generate <n> dirt instantly, low-level
        while (n > 0) {
            if (geneDirt() == false) return false;
            else n -= 1;
        }
        return true;
    }
    
    synchronized public void printRoom() {
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
                if (this.robotCoord != null && this.robotCoord.x == j && this.robotCoord.y == i) {
                    System.out.print(" + |");
                }
                else if (c.hasDirt() && c.hasJewel()) {
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

    synchronized public void newDirt() {
        // try to add a dirt, it will be added in the next loop
        this.addDirt = true;
    }

    synchronized public void newJewel() {
        // try to add a jewel, it will be added in the next loop
        this.addJewel = true;
    }

    synchronized public void clearCell(int index) {
        this.cells.get(index).setDirt(false);
        this.cells.get(index).setJewel(false);
    }

    synchronized public void pickJewel(int index) {
        this.cells.get(index).setJewel(false);
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            if (addJewel) {
                this.addJewel = false;
                this.geneJewel();
            }

            if (addDirt) {
                this.addDirt = false;
                this.geneDirt();
            }

            if (autoGenerate) {
                long t = System.currentTimeMillis() - lastUpdate;
                Random random = new Random(System.currentTimeMillis());
                
                this.geneDirt((int)(random.nextGaussian()/2 + t/1000 * dirtGeneratePerSecond));
                this.geneJewel((int)(random.nextGaussian()/2 + t/1000 * jewelGeneratePerSecond));
                lastUpdate = System.currentTimeMillis();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // don't wake me...
            }

        }
    }

    synchronized public void setRobotCoord(Vec2Int v) {
        this.robotCoord = new Vec2Int(v.x, v.y);
    }
}
