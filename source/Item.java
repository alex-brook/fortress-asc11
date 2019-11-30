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

    private String imageFile;

    static {
        FIRE_BOOTS.imageFile = "brown_boots.png";
        FLIPPERS.imageFile = "silver_boots.png";
        TOKEN.imageFile = "bag_coins.png";
        RED_KEY.imageFile =  "key_red.png";
        GREEN_KEY.imageFile =  "key_green.png";
        BLUE_KEY.imageFile = "key_blue.png";
    }

    private final char mapChar;

    private Item(final char mapc) {
        this.mapChar = mapc;
    }

    /**
     *
     * @return
     */
    public String getImageName() {
        return imageFile;
    }

    /**
     *
     * @return
     */
    public char getMapChar() {
        return mapChar;
    }

    /**
     *
     * @param c
     * @return
     */
    public static Item getItem(final char c) {
        for (Item i : values()) {
            if (i.getMapChar() == c) {
                return i;
            }
        }
        return null;
    }

}