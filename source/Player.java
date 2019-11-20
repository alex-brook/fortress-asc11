class Player {
    public static final char PLAYER = 'P';
    private int xPos;
    private int yPos;

    private Item[] inventory;
    private int tokenCount;

    Player(final int x, final int y, final String[] addInfo) {
        this(x, y, Integer.parseInt(addInfo[0]), inventoryFromInfo(addInfo));
    }

    private Player(final int x, final int y, final int tokens, final Item[] inv) {
        xPos = x;
        yPos = y;
        tokenCount = tokens;
        inventory = inv;
    }

    private static Item[] inventoryFromInfo(final String[] info) {
        Item[] inv = new Item[info.length - 1];
        for (int i = 0; i < inv.length; i++) {
            inv[i] = Item.getItem(info[i + 1].charAt(0));
        }
        return inv;
    }

    public final int getXPos() {
        return xPos;
    }

    public final int getYPos() {
        return yPos;
    }

    public final char getMapChar() {
        return PLAYER;
    }
    public final String getAdditionalInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokenCount);
        for (Item i : inventory) {
            sb.append(GameState.INFO_DELIMITER);
            sb.append(i.getMapChar());
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return String.valueOf(getMapChar());
    }
}
