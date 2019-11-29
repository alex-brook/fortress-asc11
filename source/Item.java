/**
 * Definitions for items
 *
 * @author Alex
 */
public enum Item{
    FIRE_BOOTS(TileFactory.MapChars.FIRE_BOOTS),
    FLIPPERS(TileFactory.MapChars.FLIPPERS),
    TOKEN(TileFactory.MapChars.TOKEN),
    RED_KEY(TileFactory.MapChars.RED_KEY),
    GREEN_KEY(TileFactory.MapChars.GREEN_KEY),
    BLUE_KEY(TileFactory.MapChars.BLUE_KEY);

    private final char mapChar;

    private Item(final char mapc) {
        this.mapChar = mapc;
    }

    public char getMapChar() {
        return mapChar;
    }
    public static Item getItem(final char c) {
        for (Item i : values()) {
            if (i.getMapChar() == c) {
                return i;
            }
        }
        return null;
    }

}