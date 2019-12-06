/**
 * Collection of directions, used to change the sprite of the player and enemy
 * depending on the direction they are "facing"
 *
 * @author Alex
 */
public enum Direction {
    UP("up") {
        @Override
        public Direction opposite() {
            return DOWN;
        }
        @Override
        public Direction turnleft() {
         return LEFT;
        }
        @Override
        public Direction turnright() {
            return RIGHT;
        }
    },
    DOWN("down") {
        @Override
        public Direction opposite() {
            return UP;
        }
        @Override
        public Direction turnleft() {
            return RIGHT;
        }
        @Override
        public Direction turnright() {
            return LEFT;
        }
    },
    LEFT("left") {
        @Override
        public Direction opposite() {
            return RIGHT;
        }
        @Override
        public Direction turnleft() {
            return DOWN;
        }
        @Override
        public Direction turnright() {
            return UP;
        }
    },
    RIGHT("right") {
        @Override
        public Direction opposite() {
            return LEFT;
        }
        @Override
        public Direction turnleft() {
            return UP;
        }
        @Override
        public Direction turnright() {
            return DOWN;
        }
    };

    private String mapStr;

    private Direction(final String dirStr) {
        mapStr = dirStr;
    }

    public abstract Direction opposite();
    public abstract Direction turnleft();
    public abstract Direction turnright();
    /**
     * Returns correct direction for input
     * @param str
     * @return
     */
    public static Direction getDirection(final String str) {
        for (Direction d : values()) {
            if (d.toString().equals(str)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Generic toString override
     * @return wanted toString output
     */
    @Override
    public String toString() {
        return mapStr;
    }
}
