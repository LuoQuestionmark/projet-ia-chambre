//  cleaner can clear a cell in the room

package Robot;

import Rooms.Room;

public class Cleaner {
    private Room room;

    public Cleaner (Room r) {
        this.room = r;
    }

    public void cleanCell(int r, int c) {
        if (r < 0 || c < 0) return;
        if (r > 5 || c > 5) return;

        int index = r * 5 + c;
        this.room.clearCell(index);
    }

    public void pickJewel(int r, int c) {
        if (r < 0 || c < 0) return;
        if (r > 5 || c > 5) return;

        int index = r * 5 + c;
        this.room.pickJewel(index);
    }
}
