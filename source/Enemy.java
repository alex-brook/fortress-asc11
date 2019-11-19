public abstract class Enemy{
    public static final class MapChars {
        public static final char WALL_FOLLOW_ENEMY = 'W';
        public static final char DUMB_TARGET_ENEMY = 'D';
        public static final char SMART_TARGET_ENEMY = 'S';
        public static final char STRAIGHT_LINE_ENEMY = 's';

        public static final char[] ALL = {WALL_FOLLOW_ENEMY, DUMB_TARGET_ENEMY,
        SMART_TARGET_ENEMY, STRAIGHT_LINE_ENEMY};
    }

    public static final boolean isEnemy(final char c){
        for (char mapChar : MapChars.ALL) {
            if (mapChar == c) {
                return true;
            }
        }
        return false;
    }
}