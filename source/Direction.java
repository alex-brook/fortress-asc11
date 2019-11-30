/**
 * Collection of directions, used to change the sprite of the player and enemy
 * depending on the direction they are "facing"
 *
 * @author Alex
 */
public enum Direction {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right");

    private String mapStr;

    private Direction(final String dirStr) {
        mapStr = dirStr;
    }

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
