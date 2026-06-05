package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0,1),
    EAST(1,0),
    SOUTH(0,-1),
    WEST(-1,0),

    NORTHEAST(1,1),
    NORTHWEST(-1,1),
    SOUTHEAST(1,-1),
    SOUTHWEST(-1,-1),

    NONE(0,0);

    private final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() { return dx; }
    public int getDy() { return dy; }

    public float getAngle(){
        if (dx == 1) {
            if (dy == 1) return 315;
            else if (dy == -1) return 225;
            else return 270;
        }
        else if (dx == -1) {
            if (dy == 1) return 45;
            else if (dy == -1) return 135;
            else return 90;
        }
        else if (dy == -1) return 180;
        return 0;
    }

    public static Direction fromAngle(float angle) {
        int angleToInt = (int) angle;
        switch (angleToInt){
            case 0: return NORTH;
            case 45: return NORTHWEST;
            case 90: return WEST;
            case 135: return SOUTHWEST;
            case 180: return SOUTH;
            case 225: return SOUTHEAST;
            case 270: return EAST;
            default: return NORTHEAST;
        }
    }

    public Direction combine(Direction other) {
        if (other == null || other == NONE) return this;

        int newDx = adjustValue(dx + other.dx);
        int newDy = adjustValue(dy + other.dy);

        Direction[] directions = Direction.values();
        for (Direction direction : directions)
            if (direction.dx == newDx && direction.dy == newDy)
                return direction;

        return this;
    }

    private int adjustValue(int value) {
        if (value == 2) return 1;
        if (value == -2) return -1;
        return value;
    }

    public static Direction getRandomDirection() {
        Direction[] directions = values();
        return directions[(int) (Math.random() * 9)];
    }
}
