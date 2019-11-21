import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Player {
    public static final char PLAYER = 'P';
    private int xPos;
    private int yPos;

    private List<Item> inventory;
    private int tokenCount;

    private boolean won;
    private boolean dead;

    Player(final int x, final int y, final String[] addInfo) {
        this(x, y, Integer.parseInt(addInfo[0]), inventoryFromInfo(addInfo));
    }

    private Player(final int x, final int y, final int tokens, final Item[] inv) {
        xPos = x;
        yPos = y;
        tokenCount = tokens;
        inventory = new LinkedList<>(Arrays.asList(inv));
    }

    private static Item[] inventoryFromInfo(final String[] info) {
        Item[] inv = new Item[info.length - 1];
        for (int i = 0; i < inv.length; i++) {
            inv[i] = Item.getItem(info[i + 1].charAt(0));
        }
        return inv;
    }

    public void giveItem(final Item item) {
        inventory.add(item);
    }

    public void takeItem(final Item item) {
        inventory.remove(item);
    }

    public boolean hasItem(final Item item) {
        return inventory.contains(item);
    }

    public void giveToken() {
        tokenCount++;
    }

    public void takeTokens(final int count) {
        tokenCount -= count;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void teleportTo(final int newX, final int newY) {
        xPos = newX;
        yPos = newY;
    }

    public void moveLeft() {
        xPos -= 1;
    }

    public void moveRight() {
        xPos += 1;
    }

    public int getXPos() {
        return xPos;
    }

    public void moveUp() {
        yPos -= 1;
    }

    public void moveDown() {
        yPos += 1;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        dead = true;
    }

    public boolean hasWon() {
        return won;
    }

    public void win() {
        won = true;
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
