
public final class TileFactory {
    public static final class MapChars {
        //Tiles
        public static final char WALL = '#';
        public static final char GROUND = ' ';
        public static final char WATER = '~';
        public static final char FIRE = '&';
        public static final char GOAL = '!';
        public static final char KEY_DOOR = '@';
        public static final char SCORE_DOOR = '$';
        public static final char TELEPORTER = 'T';
        //Items
        public static final char TOKEN = 'O';
        public static final char FIRE_BOOTS = '"';
        public static final char FLIPPERS = 'Y';
        public static final char RED_KEY = 'r';
        public static final char BLUE_KEY = 'b';
        public static final char GREEN_KEY = 'g';
    }

    public Tile getTile(final char c) {
        return getTile(c, new String[0]);
    }

    public Tile getTile(final char c, final String additionalInfo) {
        return getTile(c, additionalInfo.split(GameState.INFO_DELIMITER));
    }

    public Tile getTile(final char c, final String[] additionalInfo) {
        switch (c) {
            case MapChars.GROUND:
                return new Ground(c);
            case MapChars.WALL:
                return new Wall(c);
            case MapChars.FIRE:
                return new Hazard(c, Item.FIRE_BOOTS);
            case MapChars.WATER:
                return new Hazard(c, Item.FLIPPERS);
            case MapChars.GOAL:
                return new Goal(c);
            case MapChars.KEY_DOOR:
                return new KeyDoor(c, keyFromChar(additionalInfo[0].charAt(0)));
            case MapChars.SCORE_DOOR:
                return new ScoreDoor(c, Integer.parseInt(additionalInfo[0]));
            case MapChars.TELEPORTER:
                return new Teleporter(c, Integer.parseInt(additionalInfo[0]),
                        Integer.parseInt(additionalInfo[1]));
            case MapChars.TOKEN:
                return new Ground(c, Item.TOKEN);
            case MapChars.FIRE_BOOTS:
                return new Ground(c, Item.FIRE_BOOTS);
            case MapChars.FLIPPERS:
                return new Ground(c, Item.FLIPPERS);
            case MapChars.RED_KEY:
                return new Ground(c, Item.RED_KEY);
            case MapChars.GREEN_KEY:
                return new Ground(c, Item.GREEN_KEY);
            case MapChars.BLUE_KEY:
                return new Ground(c, Item.BLUE_KEY);
            default:
                return new Ground(c);
        }
    }
    private Item keyFromChar(final char c) {
        switch (c) {
            case MapChars.RED_KEY:
                return Item.RED_KEY;
            case MapChars.GREEN_KEY:
                return Item.GREEN_KEY;
            case MapChars.BLUE_KEY:
                return Item.BLUE_KEY;
            default:
                return null;
        }
    }
}
