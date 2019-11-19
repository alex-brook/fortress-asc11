public abstract class Tile {
    public static final class MapChars {
        public static final char WALL = '#';
        public static final char GROUND = ' ';
        public static final char WATER = '~';
        public static final char FIRE = '&';
        public static final char GOAL = '!';
        public static final char KEY_DOOR = '@';
        public static final char SCORE_DOOR = '$';
        public static final char TELEPORTER = 'T';
        public static final char TOKEN = 'O';
        public static final char FIRE_BOOTS = '"';
        public static final char FLIPPERS = 'Y';
        public static final char RED_KEY = 'r';
        public static final char BLUE_KEY = 'b';
        public static final char GREEN_KEY = 'g';

        public static final char[] ALL = {WALL, GROUND, WATER, FIRE, GOAL,
        KEY_DOOR, SCORE_DOOR, TELEPORTER, TOKEN, FIRE_BOOTS, FLIPPERS,
        RED_KEY, BLUE_KEY, GREEN_KEY};
    }
    public static boolean isTile(final char c) {
        for (char mapChar : MapChars.ALL) {
            if (mapChar == c) {
                return true;
            }
        }
        return false;
    }
    //Wpublic abstract char getMapChar();
}