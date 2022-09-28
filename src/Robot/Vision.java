// use this class to simulate the vision of robot

package Robot;

import java.util.ArrayList;

import Rooms.Room;
import myUtil.Vec2Int;

public class Vision {
    private Room room;
    public Vision(Room r) {
        this.room = r;
    }

    public ArrayList<Vec2Int> getDirtyCellsIndex() {
        // get all the coordinates of dirty rooms
        // return: (r1, c1), (r2, c2), ...
        ArrayList<Vec2Int> out = new ArrayList<>();

        ArrayList<Integer> cleanIndexs = this.room.getCleanCellsIndex();
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (cleanIndexs.contains(r * 5 + c)) continue;
                out.add(new Vec2Int(c, r));
            }
        }
        return out;
    }

    public ArrayList<Vec2Int> getJewelCellsIndex() {
        // get all the coordinates of jewel rooms
        // return: (r1, c1), (r2, c2), ...
        ArrayList<Vec2Int> out = new ArrayList<>();

        ArrayList<Integer> cleanIndexs = this.room.getNoJewelCellsIndex();
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (cleanIndexs.contains(r * 5 + c)) continue;
                out.add(new Vec2Int(c, r));
            }
        }
        return out;
    }
}
