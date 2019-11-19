public final class EnemyFactory {
    private static final String INFO_DELIMITER = ",";

    public Enemy getEnemy(final char c) {
        return getEnemy(c, new String[0]);
    }

    public Enemy getEnemy(final char c, final String additionalInfo) {
        return getEnemy(c, additionalInfo.split(INFO_DELIMITER));
    }

    public Enemy getEnemy(final char c, final String[] additionalInfo) {
        switch (c) {
            case Enemy.MapChars.DUMB_TARGET_ENEMY:
                return new DumbTargetEnemy();
            case Enemy.MapChars.SMART_TARGET_ENEMY:
                return new SmartTargetEnemy();
            case Enemy.MapChars.STRAIGHT_LINE_ENEMY:
                //pass direction
                return new StraightLineEnemy(additionalInfo[0]);
            case Enemy.MapChars.WALL_FOLLOW_ENEMY:
                return new WallFollowEnemy();
            default:
                throw new IllegalArgumentException("Invalid enemy.");
        }
    }
}
