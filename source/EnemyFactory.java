public final class EnemyFactory {
    private static final class MapChars {
        public static final char WALL_FOLLOW_ENEMY = 'W';
        public static final char DUMB_TARGET_ENEMY = 'D';
        public static final char SMART_TARGET_ENEMY = 'S';
        public static final char STRAIGHT_LINE_ENEMY = 's';
    }

    private static final String INFO_DELIMITER = ",";

    public Enemy getEnemy(final char c) {
        return getEnemy(c, new String[0]);
    }

    public Enemy getEnemy(final char c, final String additionalInfo) {
        return getEnemy(c, additionalInfo.split(INFO_DELIMITER));
    }

    public Enemy getEnemy(final char c, final String[] additionalInfo) {
        switch (c) {
            case MapChars.DUMB_TARGET_ENEMY:
                return new DumbTargetEnemy();
            case MapChars.SMART_TARGET_ENEMY:
                return new SmartTargetEnemy();
            case MapChars.STRAIGHT_LINE_ENEMY:
                return new StraightLineEnemy(additionalInfo[0]);
            case MapChars.WALL_FOLLOW_ENEMY:
                return new WallFollowEnemy();
            default:
                return null;
        }
    }
}
