package myUtil;

public class Vec2Int {
    public int x, y;
    public Vec2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Vec2Int)) {
            return false;
        }
        Vec2Int tmp = (Vec2Int)o;
        if (tmp.x == this.x && tmp.y == this.y) {
            return true;
        }
        else return false;
    }
}
