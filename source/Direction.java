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

    public static Direction getDirection(final String str) {
        for (Direction d : values()) {
            if (d.toString().equals(str)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mapStr;
    }
}
