
public final class TileFactory {
    private static final String INFO_DELIMITER = ",";

    public Tile getTile(final char c) {
        return getTile(c, new String[0]);
    }

    public Tile getTile(final char c, final String additionalInfo) {
        return getTile(c, additionalInfo.split(INFO_DELIMITER));
    }

    public Tile getTile(final char c, final String[] additionalInfo) {
        switch (c) {
            case Tile.MapChars.GROUND:
                return new Ground();
            case Tile.MapChars.WALL:
                return new Wall();
            case Tile.MapChars.FIRE:
                // pass in fire boots as item
                return new Hazard();
            case Tile.MapChars.WATER:
                // pass in flippers as item
                return new Hazard();
            case Tile.MapChars.GOAL:
                return new Goal();
            case Tile.MapChars.KEY_DOOR:
                return new KeyDoor(keyFromColor(additionalInfo[0]));
            case Tile.MapChars.SCORE_DOOR:
                return new ScoreDoor(Integer.parseInt(additionalInfo[0]));
            case Tile.MapChars.TELEPORTER:
                // pass in linked teleporter
                return new Teleporter();
            case Tile.MapChars.TOKEN:
                return new Ground(Item.TOKEN);
            case Tile.MapChars.FIRE_BOOTS:
                return new Ground(Item.FIRE_BOOTS);
            case Tile.MapChars.FLIPPERS:
                return new Ground(Item.FLIPPERS);
            case Tile.MapChars.RED_KEY:
                return new Ground(Item.RED_KEY);
            case Tile.MapChars.GREEN_KEY:
                return new Ground(Item.GREEN_KEY);
            case Tile.MapChars.BLUE_KEY:
                return new Ground(Item.BLUE_KEY);
            default:
                return null;
        }
    }

    private Item keyFromColor(final String color) {
        switch (color) {
            case "Red":
                return Item.RED_KEY;
            case "Green":
                return Item.GREEN_KEY;
            case "Blue":
                return Item.BLUE_KEY;
            default:
                throw new IllegalArgumentException("Invalid key color.");
        }
    }
}
