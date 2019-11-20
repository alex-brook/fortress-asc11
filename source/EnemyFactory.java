import java.util.Map;

public final class EnemyFactory {
    private static final class MapChars {
        public static final char WALL_FOLLOW_ENEMY = 'W';
        public static final char DUMB_TARGET_ENEMY = 'D';
        public static final char SMART_TARGET_ENEMY = 'S';
        public static final char STRAIGHT_LINE_ENEMY = 's';
    }

    public Enemy getEnemy(final char c, final int x, final int y) {
        return getEnemy(c, x, y, new String[0]);
    }

    public Enemy getEnemy(final char c, final int x, final int y,
                          final String additionalInfo) {
        return getEnemy(c, x, y,
                additionalInfo.split(GameState.INFO_DELIMITER));
    }

    public Enemy getEnemy(final char c, final int x, final int y,
                          final String[] additionalInfo) {
        switch (c) {
            case MapChars.DUMB_TARGET_ENEMY:
                return new DumbTargetEnemy(x, y, MapChars.DUMB_TARGET_ENEMY);
            case MapChars.SMART_TARGET_ENEMY:
                return new SmartTargetEnemy(x, y, MapChars.SMART_TARGET_ENEMY);
            case MapChars.STRAIGHT_LINE_ENEMY:
                return new StraightLineEnemy(x, y, MapChars.STRAIGHT_LINE_ENEMY,
                        additionalInfo[0]);
            case MapChars.WALL_FOLLOW_ENEMY:
                return new WallFollowEnemy(x, y, MapChars.WALL_FOLLOW_ENEMY);
            default:
                return null;
        }
    }
}
